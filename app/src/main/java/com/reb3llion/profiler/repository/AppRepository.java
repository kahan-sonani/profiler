package com.reb3llion.profiler.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.reb3llion.profiler.room.ProfileDatabase;
import com.reb3llion.profiler.room.dao.ProfileDao;
import com.reb3llion.profiler.room.entities.Profile;
import com.reb3llion.profiler.room.tuple.ProfileTuple;

import java.util.List;

public class AppRepository {

    private ProfileDao profileDao;

    public AppRepository(Application application){
        profileDao = ProfileDatabase.getInstance(application.getApplicationContext()).ProfileDao();
    }
    public Long addProfile(Profile profile){
        return profileDao.insert(profile);
    }

    public Integer updateProfile(Profile profile){
        return profileDao.update(profile);
    }

    public Integer enableDisableProfile(Profile profile, boolean update){
        return profileDao.enableDisableProfile(update, profile.getId());
    }

    public Integer deleteProfile(Profile profile) { return profileDao.delete(profile); }

    public Integer deleteProfile(ProfileTuple profileTuple) { return profileDao.delete(profileTuple.startTime, profileTuple.endTime); }

    public LiveData<List<ProfileTuple>> getAllProfileTuples(){
        return profileDao.getAllStartEndTimeWithDays();
    }

    public LiveData<List<Profile>> getAllProfiles(){
        return profileDao.getAll();
    }
}
