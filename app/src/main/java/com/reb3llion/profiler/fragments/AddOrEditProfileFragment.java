package com.reb3llion.profiler.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.reb3llion.profiler.R;
import com.reb3llion.profiler.databinding.FragmentAddEditProfileBinding;
import com.reb3llion.profiler.models.AddEditProfileFragmentModel;
import com.reb3llion.profiler.models.ListProfileFragmentModel;
import com.reb3llion.profiler.room.entities.Profile;
import com.reb3llion.profiler.utils.MODE;
import com.reb3llion.profiler.utils.STATUS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AddOrEditProfileFragment extends Fragment{

    private FragmentAddEditProfileBinding binding;
    private AddEditProfileFragmentModel model;
    private OnBackPressedCallback callback;

    private static final String TIME_PICKER_TAG = "time_picker_tag";

    public AddOrEditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        model = new ViewModelProvider(this)
                .get(AddEditProfileFragmentModel.class);

        model.initFragment(this);

        callback = model.getCallback(this);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_profile, container, false);
        binding.setModel(model);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addUpdateProfile.setOnClickListener(v -> model.addUpdateProfile());
        model.invalidCredentials.observe(getViewLifecycleOwner(), status -> Snackbar.make(requireView(), status.getMessage(requireContext()), BaseTransientBottomBar.LENGTH_LONG)
                .setAnchorView(binding.addUpdateProfile)
                .show());

        model.addProfileSuccessful.observe(getViewLifecycleOwner(), status -> NavHostFragment.findNavController(AddOrEditProfileFragment.this)
                .popBackStack());
        initTimePickers();

    }

    private void initTimePickers() {
        binding.startTime.setOnClickListener(v -> {
            MaterialTimePicker dialog = model.initStartTimePicker();
            dialog.showNow(getChildFragmentManager(), TIME_PICKER_TAG);
            dialog.addOnPositiveButtonClickListener(v1 -> model.profile.setStartTime(new Profile.Time(dialog.getHour(), dialog.getMinute())));
        });

        binding.endTime.setOnClickListener(v -> {
            MaterialTimePicker dialog = model.initEndTimePicker();
            dialog.showNow(getChildFragmentManager(), TIME_PICKER_TAG);
            dialog.addOnPositiveButtonClickListener(v12 -> model.profile.setEndTime(new Profile.Time(dialog.getHour(), dialog.getMinute())));
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback.remove();
    }

}