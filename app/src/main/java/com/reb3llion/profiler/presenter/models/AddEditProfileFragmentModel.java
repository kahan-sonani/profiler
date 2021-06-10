package com.reb3llion.profiler.presenter.models;

import android.app.Application;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.reb3llion.profiler.R;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.business.ProfileManagement;
import com.reb3llion.profiler.domain.usecases.AsyncExecutor;
import com.reb3llion.profiler.domain.usecases.UseCaseAddOrEditProfile;
import com.reb3llion.profiler.presenter.enums.MODE;
import com.reb3llion.profiler.presenter.enums.STATUS;
import com.reb3llion.profiler.presenter.fragments.AddOrEditProfileFragmentArgs;

import java.util.Calendar;
import java.util.Objects;


public class AddEditProfileFragmentModel extends AndroidViewModel implements AsyncExecutor.AsyncCallback {

    private MODE mode;
    public SingleEvent<STATUS> addProfileSuccessful;
    public SingleEvent<STATUS> invalidCredentials;

    private UseCaseAddOrEditProfile useCaseAddOrEditProfile;

    private static final String TAG = "AddEditProfileFragmentModel";
    public Profile profile;

    public AddEditProfileFragmentModel(Application application){
        super(application);
        mode = MODE.ADD;
        profile = new Profile();
        addProfileSuccessful = new SingleEvent<>();
        invalidCredentials = new SingleEvent<>();
        useCaseAddOrEditProfile = new UseCaseAddOrEditProfile(application);
    }

    public MaterialTimePicker initStartTimePicker(){
        return getPicker(profile.getStartTime());
    }
    public MaterialTimePicker initEndTimePicker(){
        return getPicker(profile.getEndTime());
    }

    private MaterialTimePicker getPicker(String time){
        Profile.Time temp = new Profile.Time(-1, -1);
        if(Profile.PLACEHOLDER_TIME.compareTo(time) == 0) {
            temp.hour = Calendar.getInstance().get(Calendar.HOUR);
            temp.minute = Calendar.getInstance().get(Calendar.MINUTE);
        }else{
             temp = Profile.Time.ToTimeObject(time);
        }

        return new MaterialTimePicker.Builder()
                .setHour(temp.hour)
                .setMinute(temp.minute)
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build();

    }

    public boolean isProfileValid(){
        return profile.isProfileValid();
    }

    public void addUpdateProfile() {
        if(mode == MODE.ADD){
            useCaseAddOrEditProfile.addProfile(profile, this);
        }
        else {
            useCaseAddOrEditProfile.updateProfile(profile, this);
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(STATUS result) {
        addProfileSuccessful.postValue(result);
    }

    @Override
    public void onFailure(STATUS error) {
        invalidCredentials.postValue(error);
    }

    public void initFragment(Fragment fragment){
        if(fragment.getArguments() != null) {
            AddOrEditProfileFragmentArgs args = AddOrEditProfileFragmentArgs.fromBundle(fragment.getArguments());
            if (args.getMode() == MODE.UPDATE.getValue() && args.getProfileIndex() != -1) {
                this.profile = new Profile(Objects.requireNonNull(
                        ProfileManagement.getInstance().getProfiles().getValue()).get(args.getProfileIndex()));
            }
        }
    }

    public OnBackPressedCallback getCallback(Fragment fragment){
        return new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                final NavController controller = NavHostFragment.findNavController(fragment);
                if(isProfileValid() && isEnabled()) {
                    MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(fragment.requireActivity())
                            .setTitle(R.string.warning)
                            .setMessage(R.string.discard_profile)
                            .setPositiveButton(fragment.getString(R.string.yes), (dialog, which) -> {
                                dialog.dismiss();
                                controller.popBackStack();
                            })
                            .setNegativeButton(fragment.getString(R.string.no), (dialog, which) -> dialog.dismiss());
                    alertDialogBuilder.show();
                }else{
                    setEnabled(false);
                    controller.popBackStack();
                }
            }

        };
    }
    public MODE getMode() {
        return mode;
    }
}
