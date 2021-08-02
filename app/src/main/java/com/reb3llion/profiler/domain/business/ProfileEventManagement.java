package com.reb3llion.profiler.domain.business;

import android.app.AlarmManager;
import android.content.Context;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.presenter.ProfilerApplication;

public class ProfileEventManagement {

    public static final String KEY_PROFILE = "profile";
    private final AlarmManager alarmManager;

    public ProfileEventManagement() {
        alarmManager = (AlarmManager) ProfilerApplication.getAppContext()
                .getSystemService(Context.ALARM_SERVICE);
    }

    public void addProfileEvent(Profile profile) {
        profile.getState().addProfileEvent(profile, alarmManager);
    }

    public void updateProfileEvent(Profile profile) {
        profile.getState().updateProfileEvent(profile, alarmManager);
    }

    public void deleteProfileEvent(Profile profile) {
        profile.getState().deleteProfileEvent(profile, alarmManager);
    }

    public void removeProfileEvent(Profile profile) {
        profile.getState().removeProfileEvent(profile, alarmManager);
    }

    public void addProfileEventAfterBoot(Profile profile) {
        profile.getState().addProfileEventAfterBoot(profile, alarmManager);
    }

}
