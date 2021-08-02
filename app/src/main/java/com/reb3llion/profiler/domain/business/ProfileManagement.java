package com.reb3llion.profiler.domain.business;

import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.reb3llion.profiler.R;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.presenter.ProfilerApplication;

import java.util.ArrayList;
import java.util.List;

public class ProfileManagement {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getDNDPreference(String preference) {
        if (preference.equals(ProfilerApplication.getAppContext().getString(R.string.alarms_only)))
            return NotificationManager.INTERRUPTION_FILTER_ALARMS;
        else if (preference.equals(ProfilerApplication.getAppContext().getString(R.string.priority_only)))
            return NotificationManager.INTERRUPTION_FILTER_PRIORITY;
        else if (preference.equals(ProfilerApplication.getAppContext().getString(R.string.total_silence)))
            return NotificationManager.INTERRUPTION_FILTER_NONE;
        else
            return NotificationManager.INTERRUPTION_FILTER_ALL;

    }

    public static int getResourceIdFromDNDPreference(int preference) {
        if (preference == NotificationManager.INTERRUPTION_FILTER_ALARMS)
            return R.id.alarm_only;
        else if (preference == NotificationManager.INTERRUPTION_FILTER_PRIORITY)
            return R.id.priority_only;
        else if (preference == NotificationManager.INTERRUPTION_FILTER_NONE)
            return R.id.total_silence;
        else
            return R.id.normal;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getDNDPreference(int preference) {
        if (preference == NotificationManager.INTERRUPTION_FILTER_ALARMS)
            return ProfilerApplication.getAppContext().getString(R.string.alarms_only);
        else if (preference == NotificationManager.INTERRUPTION_FILTER_PRIORITY)
            return ProfilerApplication.getAppContext().getString(R.string.priority_only);
        else if (preference == NotificationManager.INTERRUPTION_FILTER_NONE)
            return ProfilerApplication.getAppContext().getString(R.string.total_silence);
        else
            return ProfilerApplication.getAppContext().getString(R.string.normal);

    }

    public static boolean doesProfileNeedDNDPermission(Profile profile) {
        return profile.getDndPreference() != 1 || (profile.getRingtoneSelected() && (profile.getRingtone()) == 0)
                || (profile.getNotificationSelected() && (profile.getNotification()) == 0);
    }

    public static boolean areBothProfileSame(Profile profile1, Profile profile2) {
        return
                profile1.getLabel().compareTo(profile2.getLabel()) == 0
                        && profile1.getStartTime().compareTo(profile2.getStartTime()) == 0
                        && profile1.getEndTime().compareTo(profile2.getEndTime()) == 0
                        && profile1.getDndPreference() == profile2.getDndPreference()
                        && profile1.getSun() == profile2.getSun()
                        && profile1.getMon() == profile2.getMon()
                        && profile1.getTue() == profile2.getTue()
                        && profile1.getWed() == profile2.getWed()
                        && profile1.getThu() == profile2.getThu()
                        && profile1.getFri() == profile2.getFri()
                        && profile1.getSat() == profile2.getSat()
                        && profile1.getState().getInt() == profile2.getState().getInt();
    }

    public static ArrayList<Boolean> getSelectedDays(Profile profile) {
        ArrayList<Boolean> days = new ArrayList<>();
        days.add(0, profile.getSun());
        days.add(1, profile.getMon());
        days.add(2, profile.getTue());
        days.add(3, profile.getWed());
        days.add(4, profile.getThu());
        days.add(5, profile.getFri());
        days.add(6, profile.getSat());
        return days;
    }

    public static boolean isStartTimeSpecified(Profile profile) {
        return Profile.Time.isTimeSpecified(profile.getStartTime());
    }

    public static boolean isEndTimeSpecified(Profile profile) {
        return Profile.Time.isTimeSpecified(profile.getEndTime());
    }

    public static boolean isNoDaySelected(Profile profile) {
        return !(profile.getSun() || profile.getMon() ||
                profile.getTue() || profile.getWed() ||
                profile.getThu() || profile.getFri() || profile.getSat());
    }

    public static boolean isNoVolumeSettingsSelected(Profile profile) {
        return profile.getDndPreference() == 1 && !(profile.getMediaSelected()
                || profile.getNotificationSelected()
                || profile.getCallSelected()
                || profile.getRingtoneSelected()
                || profile.getAlarmSelected());
    }

    public static boolean isTimeValid(Profile profile) {
        if (!(Profile.Time.isTimeSpecified(profile.getStartTime()) || Profile.Time.isTimeSpecified(profile.getEndTime())))
            return false;

        Profile.Time sTime = Profile.Time.ToTimeObject(profile.getStartTime());
        Profile.Time eTime = Profile.Time.ToTimeObject(profile.getEndTime());
        if (sTime.hour > eTime.hour)
            return false;
        else if (sTime.hour == eTime.hour) {
            return sTime.minute < eTime.minute;
        }
        return true;
    }

    public static boolean isLabelSpecified(Profile profile) {
        return Profile.PLACEHOLDER_LABEL.compareTo(profile.getLabel()) != 0;
    }

    public static boolean isProfileValid(Profile profile) {
        return isEndTimeSpecified(profile)
                && isStartTimeSpecified(profile)
                && isTimeValid(profile)
                && !isNoVolumeSettingsSelected(profile)
                && !isNoDaySelected(profile);
    }

    public static String getDayStringFromProfile(Profile tuple) {
        StringBuilder builder = new StringBuilder();
        if (tuple.getSun()) {
            builder.append(", ");
            builder.append(Profile.SUN);
        }
        if (tuple.getMon()) {
            builder.append(", ");
            builder.append(Profile.MON);
        }
        if (tuple.getTue()) {
            builder.append(", ");
            builder.append(Profile.TUE);
        }
        if (tuple.getWed()) {
            builder.append(", ");
            builder.append(Profile.WED);
        }
        if (tuple.getThu()) {
            builder.append(", ");
            builder.append(Profile.THU);
        }
        if (tuple.getFri()) {
            builder.append(", ");
            builder.append(Profile.FRI);
        }
        if (tuple.getSat()) {
            builder.append(", ");
            builder.append(Profile.SAT);
        }
        builder.delete(0, 2);
        return builder.toString();
    }

    public static String getConflictProfiles(Profile tuple, List<Profile> profiles) {
        StringBuilder builder
                = new StringBuilder();
        boolean conflict = false;
        for (int i = 0; i < profiles.size(); i++) {

            Profile s = profiles.get(i);
            if (!(tuple.getSun() && s.getSun())
                    && !(tuple.getMon() && s.getMon())
                    && !(tuple.getTue() && s.getTue())
                    && !(tuple.getWed() && s.getWed())
                    && !(tuple.getThu() && s.getThu())
                    && !(tuple.getFri() && s.getFri())
                    && !(tuple.getSat() && s.getSat())) {

                conflict = false;
            } else {
                conflict = true;
                builder.append(", ");
                builder.append(s.getLabel());
                Log.i("hii", s.getLabel());
            }
        }
        if (conflict) {
            builder.delete(0, 1);
            builder.insert(0, ProfilerApplication.getAppContext().getString(R.string.error_conflict_message));
            return builder.toString();
        } else
            return null;

    }

}