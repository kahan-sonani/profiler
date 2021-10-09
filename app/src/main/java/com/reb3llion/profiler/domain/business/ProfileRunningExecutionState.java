package com.reb3llion.profiler.domain.business;

import android.app.AlarmManager;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.usecases.UseCaseEndProfileExecution;
import com.reb3llion.profiler.domain.usecases.UseCaseStartProfileExecution;
import com.reb3llion.profiler.presenter.enums.MODE;

import java.util.Calendar;

public class ProfileRunningExecutionState extends ProfileExecutionState {

    @Override
    public void addProfileEvent(Profile profile, AlarmManager alarmManager) {
        addEventLogic(profile, alarmManager);
    }

    @Override
    public void removeProfileEvent(Profile profile, AlarmManager alarmManager) {
        removeEventLogic(profile, alarmManager);
        UseCaseEndProfileExecution useCaseEndProfileExecution = new UseCaseEndProfileExecution();
        if (!(PermissionManager.isDNDPermissionNotGranted()
                && ProfileManagement.doesProfileNeedDNDPermission(profile))) {
            useCaseEndProfileExecution.applyVolumeSettingsFromPreference(profile);
        }
        useCaseEndProfileExecution.changeState(profile);
        VibrationManager.vibrate();
    }

    @Override
    public void updateProfileEvent(Profile profile, AlarmManager alarmManager) {
        if (profile.getEnable() && !(PermissionManager.isDNDPermissionNotGranted()
                && ProfileManagement.doesProfileNeedDNDPermission(profile))) {
            Calendar calendar = Calendar.getInstance();
            Calendar sysTime = Calendar.getInstance();

            sysTime.set(Calendar.SECOND, 0);
            sysTime.set(Calendar.MILLISECOND, 0);
            setProfileTimeCalendar(profile.getStartTime(), calendar);

            if (calendar.compareTo(sysTime) > 0) {
                removeProfileEvent(profile, alarmManager);
                setEventAlarm(getPendingIntent(profile, getStartEventIntent(profile)),
                        calendar.getTimeInMillis(), alarmManager);
                setProfileTimeCalendar(profile.getEndTime(), calendar);
            } else {
                UseCaseStartProfileExecution useCaseStartProfileExecution =
                        new UseCaseStartProfileExecution();
                useCaseStartProfileExecution.applyVolumeSettings(profile, MODE.UPDATE);
                VibrationManager.vibrate();
                setProfileTimeCalendar(profile.getEndTime(), calendar);
                if (calendar.compareTo(sysTime) >= 0) {
                    removeEventLogic(profile, alarmManager);
                } else {
                    removeProfileEvent(profile, alarmManager);
                    setProfileTimeCalendar(profile.getStartTime(), calendar);
                    calendar.add(Calendar.DAY_OF_WEEK, calculateNextDay(
                            sysTime.get(Calendar.DAY_OF_WEEK), ProfileManagement.getSelectedDays(profile)));
                    setEventAlarm(getPendingIntent(profile, getStartEventIntent(profile)),
                            calendar.getTimeInMillis(), alarmManager);
                    setProfileTimeCalendar(profile.getEndTime(), calendar);
                }
            }
            setEventAlarm(getPendingIntent(profile, getEndEventIntent(profile)),
                    calendar.getTimeInMillis(), alarmManager);

        }

    }

    @Override
    public void deleteProfileEvent(Profile profile, AlarmManager alarmManager) {
        deleteProfileEventLogic(profile, alarmManager);
        UseCaseEndProfileExecution useCaseEndProfileExecution = new UseCaseEndProfileExecution();
        if (!(PermissionManager.isDNDPermissionNotGranted()
                && ProfileManagement.doesProfileNeedDNDPermission(profile))) {
            useCaseEndProfileExecution.applyVolumeSettingsFromPreference(profile);
        }
        useCaseEndProfileExecution.changeState(profile);
        VibrationManager.vibrate();
    }

    @Override
    public void addProfileEventAfterBoot(Profile profile, AlarmManager alarmManager) {
        Calendar calendar = Calendar.getInstance();
        setProfileTimeCalendar(profile.getEndTime(), calendar);
        setEventAlarm(getPendingIntent(profile, getEndEventIntent(profile))
                , calendar.getTimeInMillis(), alarmManager);
    }

    @Override
    public int getInt() {
        return RUNNING;
    }
}
