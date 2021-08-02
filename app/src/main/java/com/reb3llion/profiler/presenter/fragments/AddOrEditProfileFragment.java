package com.reb3llion.profiler.presenter.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

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
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.databinding.DndPreferencesBinding;
import com.reb3llion.profiler.databinding.FragmentAddEditProfileBinding;
import com.reb3llion.profiler.databinding.LabelEditTextBinding;
import com.reb3llion.profiler.databinding.VolumeSliderLayoutBinding;
import com.reb3llion.profiler.domain.business.ProfileManagement;
import com.reb3llion.profiler.domain.business.StreamMinMaxValuesStore;
import com.reb3llion.profiler.domain.business.TimeFormat;
import com.reb3llion.profiler.presenter.models.AddEditProfileFragmentModel;

import java.util.Objects;


public class AddOrEditProfileFragment extends Fragment implements View.OnClickListener {

    private FragmentAddEditProfileBinding binding;
    private AddEditProfileFragmentModel model;
    private OnBackPressedCallback callback;
    private VolumeSliderLayoutBinding sliderBinding;
    private LabelEditTextBinding editTextBinding;
    private DndPreferencesBinding dndPreferencesBinding;

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

        initDNDPreference();
        initLabelView();
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
        sliderBinding = VolumeSliderLayoutBinding.inflate(getLayoutInflater());
        binding.alarmVolume.setOnClickListener(this);
        binding.notificationVolume.setOnClickListener(this);
        binding.mediaVolume.setOnClickListener(this);
        binding.ringtoneVolume.setOnClickListener(this);
        binding.callVolume.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        StreamMinMaxValuesStore streamMinMaxValuesStore = new StreamMinMaxValuesStore();
        MaterialAlertDialogBuilder builder = model.getDialog(requireContext());
        builder.setOnDismissListener(dialog -> ((ViewGroup) sliderBinding.getRoot().getParent()).removeView(sliderBinding.getRoot()));
        builder.setView(sliderBinding.getRoot());
        Slider slider = sliderBinding.volumeSlider;
        int id = v.getId();
        if (id == R.id.alarm_volume) {
            slider.setValueTo(streamMinMaxValuesStore.getAlarmMax());
            slider.setValue(model.profile.getAlarm());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.alarm))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setAlarm((int) slider.getValue()));
        } else if (id == R.id.media_volume) {
            slider.setValueTo(streamMinMaxValuesStore.getMediaMax());
            slider.setValue(model.profile.getMedia());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.media))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setMedia((int) slider.getValue()));
        } else if (id == R.id.notification_volume) {
            slider.setValueTo(streamMinMaxValuesStore.getNotificationMax());
            slider.setValue(model.profile.getNotification());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.notification))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setNotification((int) slider.getValue()));
        } else if (id == R.id.ringtone_volume) {
            slider.setValueTo(streamMinMaxValuesStore.getRingtoneMax());
            slider.setValue(model.profile.getRingtone());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.ringtone))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setRingtone((int) slider.getValue()));
        } else if (id == R.id.call_volume) {
            slider.setValueFrom(1);
            slider.setValueTo(streamMinMaxValuesStore.getCallMax());
            slider.setValue(model.profile.getCall());
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setTitle(getString(R.string.call))
                    .setPositiveButton(getString(R.string.yes),
                            (dialog, which) -> model.profile.setCall((int) slider.getValue()));
        }
        builder.show();
    }

    private void initLabelView() {
        editTextBinding = LabelEditTextBinding.inflate(getLayoutInflater());
        binding.profileLabel.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = model.getDialog(requireContext());
            builder.setOnDismissListener(dialog -> ((ViewGroup) editTextBinding.getRoot().getParent()).removeView(editTextBinding.getRoot()));
            builder.setView(editTextBinding.getRoot());
            if (ProfileManagement.isLabelSpecified(model.profile))
                editTextBinding.label.setText(model.profile.getLabel());
            editTextBinding.label.requestFocus();
            builder.setPositiveButton(R.string.ok, (dialog, which) -> {
                if ("".equals(Objects.requireNonNull(editTextBinding.label.getText()).toString()))
                    binding.profileLabel.setText(Profile.PLACEHOLDER_LABEL);
                else
                    binding.profileLabel.setText(editTextBinding.label.getText());

            }).setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }

    private void initDNDPreference() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dndPreferencesBinding = DndPreferencesBinding.inflate(getLayoutInflater());
            binding.dndLayout.setOnClickListener(v -> {
                dndPreferencesBinding.radiogrp.check(ProfileManagement.getResourceIdFromDNDPreference(model.profile.getDndPreference()));
                model.getDialog(requireContext())
                        .setOnDismissListener(dialog -> ((ViewGroup) dndPreferencesBinding.getRoot().getParent()).removeView(dndPreferencesBinding.getRoot()))
                        .setView(dndPreferencesBinding.getRoot())
                        .setPositiveButton(R.string.ok, (dialog, which) -> {
                            RadioButton selected = dndPreferencesBinding.getRoot()
                                    .findViewById(dndPreferencesBinding.radiogrp.getCheckedRadioButtonId());

                            model.profile.setDndPreference(ProfileManagement.getDNDPreference(selected.getText().toString()));
                        })
                        .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                        .show();
            });
        }
    }
}