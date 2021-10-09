package com.reb3llion.profiler.domain.business;

import android.app.AlarmManager;

import com.reb3llion.profiler.data.repository.room.entities.Profile;

public class ProfileNotRunningExecutionState extends ProfileExecutionState {

    @Override
    public void addProfileEvent(Profile profile, AlarmManager alarmManager) {
        addEventLogic(profile, alarmManager);
    }

    @Override
    public void removeProfileEvent(Profile profile, AlarmManager alarmManager) {
        removeEventLogic(profile, alarmManager);
    }

    @Override
    public void updateProfileEvent(Profile profile, AlarmManager alarmManager) {
        if (!(PermissionManager.isDNDPermissionNotGranted()
                && ProfileManagement.doesProfileNeedDNDPermission(profile)))
            updateEventLogic(profile, alarmManager);
    }

    @Override
    public void deleteProfileEvent(Profile profile, AlarmManager alarmManager) {
        deleteProfileEventLogic(profile, alarmManager);
    }

    @Override
    public void addProfileEventAfterBoot(Profile profile, AlarmManager alarmManager) {
        addEventLogic(profile, alarmManager);
    }

    @Override
    public int getInt() {
        return NOT_RUNNING;
    }
}
