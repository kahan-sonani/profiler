package com.reb3llion.profiler.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.reb3llion.profiler.data.repository.room.ProfileDatabase;
import com.reb3llion.profiler.data.repository.room.dao.ProfileDao;
import com.reb3llion.profiler.data.repository.room.dao.VolumeSettingsDao;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.data.repository.room.entities.VolumeSettings;
import com.reb3llion.profiler.data.repository.room.tuple.ProfileTuple;

import java.util.List;

public class AppRepository {

    private final ProfileDao profileDao;
    private final VolumeSettingsDao volumeSettingsDao;

    public AppRepository(Application application) {
        profileDao = ProfileDatabase.getInstance(application.getApplicationContext()).ProfileDao();
        volumeSettingsDao = ProfileDatabase.getInstance(application.getApplicationContext()).volumeSettingsDao();
    }

    public long addProfile(Profile profile) {
        return profileDao.insertProfile(profile);
    }

    public Integer updateProfile(Profile profile) {
        return profileDao.updateProfile(profile);
    }

    public Integer enableDisableProfile(Profile profile, boolean update) {
        return profileDao.enableDisableProfile(update, profile.getId());
    }

    public Integer deleteProfile(Profile profile) {
        return profileDao.deleteProfile(profile);
    }

    public Integer deleteProfile(ProfileTuple profileTuple) {
        return profileDao.delete(profileTuple.startTime, profileTuple.endTime);
    }

    public LiveData<List<ProfileTuple>> getAllProfileTuples() {
        return profileDao.getAllStartEndTimeWithDays();
    }

    public LiveData<List<Profile>> getAllProfilesLiveData() {
        return profileDao.getAll();
    }

    public LiveData<Profile> getProfile(long id) {
        return profileDao.getProfile(id);
    }

    public LiveData<List<Profile>> checkForConflictOfProfileTimePeriod(Profile profile) {
        return profileDao.checkForConflict(profile.getId(), profile.getStartTime(), profile.getEndTime());
    }

    public Long addVolumeSettings(VolumeSettings volumeSettings) {
        return volumeSettingsDao.insertVolumeSettings(volumeSettings);
    }

    public Integer deleteVolumeSettings(VolumeSettings settings) {
        return volumeSettingsDao.deleteVolumeSettings(settings);
    }

    public LiveData<VolumeSettings> getVolumeSettings(Profile profile) {
        return volumeSettingsDao.getVolumeSettings(profile.getId());
    }

    public Integer updateProfileState(Profile profile, int state) {
        return profileDao.updateProfileState(state, profile.getId());
    }


}
