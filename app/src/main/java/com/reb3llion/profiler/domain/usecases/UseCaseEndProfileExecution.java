package com.reb3llion.profiler.domain.usecases;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.data.repository.room.entities.VolumeSettings;
import com.reb3llion.profiler.domain.business.ProfileExecutionState;
import com.reb3llion.profiler.presenter.ProfilerApplication;

public class UseCaseEndProfileExecution extends BaseUseCase {

    public static final int flag = AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE;
    private final NotificationManager notificationManager;
    private LiveData<VolumeSettings> volumeSettingsLiveData;

    public UseCaseEndProfileExecution() {
        super(ProfilerApplication.getApplication());
        notificationManager = (NotificationManager) ProfilerApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void applyVolumeSettingsFromPreference(Profile profile) {

        volumeSettingsLiveData = appRepository.getVolumeSettings(profile);
        volumeSettingsLiveData.observeForever(new Observer<VolumeSettings>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onChanged(VolumeSettings preference) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    notificationManager.setInterruptionFilter(preference.getDndPreference());
                }

                if (profile.getDndPreference() == NotificationManager.INTERRUPTION_FILTER_ALL) {
                    AudioManager manager = (AudioManager) ProfilerApplication.getAppContext()
                            .getSystemService(Context.AUDIO_SERVICE);
                    manager.setStreamVolume(AudioManager.STREAM_ALARM, preference.getAlarm(), flag);
                    manager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, preference.getNotification(), flag);
                    manager.setStreamVolume(AudioManager.STREAM_RING, preference.getRingtone(), flag);
                    manager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, preference.getCall(), flag);
                    manager.setStreamVolume(AudioManager.STREAM_MUSIC, preference.getMedia(), flag);
                }
                new AsyncExecutor().execute(() -> appRepository.deleteVolumeSettings(preference));
                volumeSettingsLiveData.removeObserver(this);
            }
        });
    }

    public void addEvent(Profile profile) {
        profileEventManagement.addProfileEvent(profile);
    }

    public void changeState(Profile profile) {
        new AsyncExecutor().execute(() -> appRepository.updateProfileState(profile, ProfileExecutionState.NOT_RUNNING));
    }
}
