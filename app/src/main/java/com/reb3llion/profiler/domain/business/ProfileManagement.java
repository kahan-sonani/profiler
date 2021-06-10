package com.reb3llion.profiler.domain.business;

import android.app.AlarmManager;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.presenter.ProfilerApplication;

import java.util.List;

public class ProfileManagement {

    private LiveData<List<Profile>> profiles;
    private AlarmManager alarmManager;

    private static ProfileManagement mInstance;

    private ProfileManagement(){
        alarmManager = (AlarmManager)
                ProfilerApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
    }

    public static synchronized ProfileManagement getInstance(){
        if(mInstance == null)
            mInstance = new ProfileManagement();

        return mInstance;
    }

    public void setProfiles(LiveData<List<Profile>> profiles) {
        this.profiles = profiles;
    }

    public LiveData<List<Profile>> getProfiles() {
        return profiles;
    }

    public void addProfile(){

    }

    public static boolean doesProfileNeedDNDPermission(Profile profile){
        return profile.getDnd() || (profile.getRingtoneSelected() && ((int) profile.getRingtone()) == 0)
                || (profile.getNotificationSelected() && ((int) profile.getNotification()) == 0);
    }
    public static boolean areBothProfileSame(Profile profile1, Profile profile2){
        return
                profile1.getStartTime().compareTo(profile2.getStartTime()) == 0
                        && profile1.getEndTime().compareTo(profile2.getEndTime()) == 0
                        && profile1.getDnd() == profile2.getDnd()
                        && profile1.getSun() == profile2.getSun()
                        && profile1.getMon() == profile2.getMon()
                        && profile1.getTue() == profile2.getTue()
                        && profile1.getWed() == profile2.getWed()
                        && profile1.getThu() == profile2.getThu()
                        && profile1.getFri() == profile2.getFri()
                        && profile1.getSat() == profile2.getSat();
    }
}