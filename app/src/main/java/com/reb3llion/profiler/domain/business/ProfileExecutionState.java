package com.reb3llion.profiler.domain.business;

import static com.reb3llion.profiler.domain.business.ProfileEventManagement.KEY_PROFILE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.reb3llion.profiler.R;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.presenter.ProfilerApplication;
import com.reb3llion.profiler.presenter.receivers.ProfileEndEventBroadcastReceiver;
import com.reb3llion.profiler.presenter.receivers.ProfileStartEventBroadcastReceiver;

import java.util.Calendar;
import java.util.List;

public abstract class ProfileExecutionState {

    public static final int RUNNING = 1;
    public static final int NOT_RUNNING = 2;

    public abstract void addProfileEvent(Profile profile, AlarmManager alarmManager);

    public abstract void removeProfileEvent(Profile profile, AlarmManager alarmManager);

    public abstract void updateProfileEvent(Profile profile, AlarmManager alarmManager);

    public abstract void deleteProfileEvent(Profile profile, AlarmManager alarmManager);

    public abstract void addProfileEventAfterBoot(Profile profile, AlarmManager alarmManager);

    public abstract int getInt();

    protected void addEventLogic(Profile profile, AlarmManager alarmManager) {
        Calendar calendar = Calendar.getInstance();
        Calendar sysTime = Calendar.getInstance();

        int nextDay = 0, currentDay = sysTime.get(Calendar.DAY_OF_WEEK);
        List<Boolean> days = ProfileManagement.getSelectedDays(profile);
        sysTime.set(Calendar.SECOND, 0);
        sysTime.set(Calendar.MILLISECOND, 0);

        setProfileTimeCalendar(profile.getEndTime(), calendar);
        if (!(days.get(currentDay - 1)) || (calendar.compareTo(sysTime) <= 0 && days.get(currentDay - 1))) {
            nextDay = calculateNextDay(currentDay, days);
        }

        calendar.add(Calendar.DAY_OF_WEEK, nextDay);

        setProfileTimeCalendar(profile.getStartTime(), calendar);
        setEventAlarm(getPendingIntent(profile, getStartEventIntent(profile)),
                calendar.getTimeInMillis(), alarmManager);
        setProfileTimeCalendar(profile.getEndTime(), calendar);
        setEventAlarm(getPendingIntent(profile, getEndEventIntent(profile)),
                calendar.getTimeInMillis(), alarmManager);

    }

    protected PendingIntent getPendingIntent(Profile profile, Intent intent) {
        return PendingIntent.getBroadcast(ProfilerApplication.getAppContext(),
                (int) profile.getId(), intent
                , PendingIntent.FLAG_UPDATE_CURRENT);
    }

    protected Intent getStartEventIntent(Profile profile) {
        Intent intent = new Intent(ProfilerApplication.getAppContext(), ProfileStartEventBroadcastReceiver.class);
        intent.setAction(ProfilerApplication.getAppContext().getString(R.string.ACTION_PROFILE_BROADCAST_RECEIVER_ACTION));
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PROFILE, profile);
        intent.putExtra(KEY_PROFILE, bundle);
        return intent;
    }

    protected Intent getEndEventIntent(Profile profile) {
        Intent intent = new Intent(ProfilerApplication.getAppContext(), ProfileEndEventBroadcastReceiver.class);
        intent.setAction(ProfilerApplication.getAppContext().getString(R.string.ACTION_PROFILE_BROADCAST_RECEIVER_ACTION));
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PROFILE, profile);
        intent.putExtra(KEY_PROFILE, bundle);
        return intent;
    }

    protected int calculateNextDay(int currentDay, List<Boolean> days) {
        int i = currentDay, count = 1;
        do {
            i %= 7;
            if (days.get(i))
                return count;
            i++;
            count++;
        }
        while (i != currentDay);
        /*
        int i = currentDay;
        do {
            i %= 7;
            if (days.get(i))
                return i + 1;
            i++;
        }
        while (i != currentDay);*/
        return currentDay;
    }

    protected void deleteProfileEventLogic(Profile profile, AlarmManager alarmManager) {
        if (profile.getEnable())
            removeEventLogic(profile, alarmManager);

        PendingIntent startEventPIntent = getPendingIntent(profile, getStartEventIntent(profile));
        PendingIntent endEventPIntent = getPendingIntent(profile, getEndEventIntent(profile));

        startEventPIntent.cancel();
        endEventPIntent.cancel();
    }

    protected void removeEventLogic(Profile profile, AlarmManager alarmManager) {
        PendingIntent startEventPIntent = getPendingIntent(profile, getStartEventIntent(profile));
        PendingIntent endEventPIntent = getPendingIntent(profile, getEndEventIntent(profile));

        alarmManager.cancel(startEventPIntent);
        alarmManager.cancel(endEventPIntent);
    }

    protected void updateEventLogic(Profile profile, AlarmManager alarmManager) {
        if (profile.getEnable()) {
            removeEventLogic(profile, alarmManager);
            addEventLogic(profile, alarmManager);
        }
    }

    protected void setEventAlarm(PendingIntent EventPendingIntent, long millis, AlarmManager alarmManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC, millis, EventPendingIntent);
        else
            alarmManager.setExact(AlarmManager.RTC, millis, EventPendingIntent);
    }

    protected void setProfileTimeCalendar(String Time, Calendar calendar) {

        int Hour = TimeFormat.getHour(Time);
        int Minute = TimeFormat.getMinute(Time);

        calendar.set(Calendar.HOUR_OF_DAY, Hour);
        calendar.set(Calendar.MINUTE, Minute);

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
