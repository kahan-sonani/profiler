package com.reb3llion.profiler.presenter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.reb3llion.profiler.R;
import com.reb3llion.profiler.databinding.FragmentAddEditProfileBinding;
import com.reb3llion.profiler.presenter.models.AddEditProfileFragmentModel;
import com.reb3llion.profiler.domain.TimeFormat;


public class AddOrEditProfileFragment extends Fragment implements View.OnClickListener {

    private FragmentAddEditProfileBinding binding;
    private AddEditProfileFragmentModel model;
    private OnBackPressedCallback callback;
    private View dialogRootView;
    private Slider slider;

    private static final String TIME_PICKER_TAG = "time_picker_tag";

    public AddOrEditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this)
                .get(AddEditProfileFragmentModel.class);
        model.initFragment(this);

        callback = model.getCallback(this);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_profile, container, false);
        binding.setModel(model);
        binding.setApplication(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addUpdateProfile.setOnClickListener(v -> model.addUpdateProfile());
        model.invalidCredentials.observe(getViewLifecycleOwner(), status -> Snackbar.make(requireView(), status.getMessage(requireContext()), BaseTransientBottomBar.LENGTH_LONG)
                .setAnchorView(requireActivity().findViewById(R.id.bottom_navigation))
                .show());

        model.addProfileSuccessful.observe(getViewLifecycleOwner(), status -> NavHostFragment.findNavController(AddOrEditProfileFragment.this)
                .popBackStack());
        initTimePickers();
        initVolumeSliders();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback.remove();
    }

    private void initTimePickers() {
        binding.startTimeLayout.setOnClickListener(v -> {
            MaterialTimePicker dialog = model.initStartTimePicker();
            dialog.showNow(getChildFragmentManager(), TIME_PICKER_TAG);
            dialog.addOnPositiveButtonClickListener(v1 -> model.profile.setStartTime(TimeFormat.getTimeString(dialog.getHour(), dialog.getMinute())));
        });

        binding.endTimeLayout.setOnClickListener(v -> {
            MaterialTimePicker dialog = model.initEndTimePicker();
            dialog.showNow(getChildFragmentManager(), TIME_PICKER_TAG);
            dialog.addOnPositiveButtonClickListener(v12 -> model.profile.setEndTime(TimeFormat.getTimeString(dialog.getHour(), dialog.getMinute())));
        });
    }

    private void initVolumeSliders() {
        binding.alarmVolume.setOnClickListener(this);
        binding.notificationVolume.setOnClickListener(this);
        binding.mediaVolume.setOnClickListener(this);
        binding.ringtoneVolume.setOnClickListener(this);
        binding.callVolume.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        dialogRootView = requireActivity().getLayoutInflater()
                .inflate(R.layout.volume_slider_layout, null);
        builder.setView(dialogRootView);
        builder.setCancelable(false);
        builder.setOnDismissListener(dialog -> ((ViewGroup) dialogRootView.getParent()).removeView(dialogRootView));

        slider = dialogRootView.findViewById(R.id.volume_slider);
        int id = v.getId();
        if(id == R.id.alarm_volume){
            slider.setValue(model.profile.getAlarm());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.alarm))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setAlarm((int) slider.getValue()));
        }else if(id == R.id.media_volume ){
            slider.setValue(model.profile.getMedia());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.media))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setMedia((int) slider.getValue()));
        }else if(id == R.id.notification_volume){
            slider.setValue(model.profile.getNotification());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.notification))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setNotification((int) slider.getValue()));
        }else if(id == R.id.ringtone_volume){
            slider.setValue(model.profile.getRingtone());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.ringtone))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setRingtone((int) slider.getValue()));
        }else if(id == R.id.call_volume){
            slider.setValue(model.profile.getCall());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.call))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setCall((int) slider.getValue()));
        }
        builder.show();
    }

}