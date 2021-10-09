package com.reb3llion.profiler.domain.usecases;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.data.repository.room.entities.VolumeSettings;
import com.reb3llion.profiler.domain.business.ProfileExecutionState;
import com.reb3llion.profiler.presenter.ProfilerApplication;
import com.reb3llion.profiler.presenter.enums.MODE;

public class UseCaseStartProfileExecution extends BaseUseCaseWithDataBaseAccess {

    public static final int flag = AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE;

    private final AudioManager audioManager;
    NotificationManager notificationManager;

    public UseCaseStartProfileExecution() {
        super(ProfilerApplication.getApplication());
        audioManager = (AudioManager) ProfilerApplication.getAppContext().getSystemService(Context.AUDIO_SERVICE);
        notificationManager = (NotificationManager) ProfilerApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @SuppressLint("WrongConstant")
    public void applyVolumeSettings(Profile profile, MODE mode) {

        VolumeSettings preference = new VolumeSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mode == MODE.ADD)
                preference.setDNDMode(notificationManager.getCurrentInterruptionFilter());
            notificationManager.setInterruptionFilter(profile.getDndPreference());
        }

        if (profile.getDndPreference() == NotificationManager.INTERRUPTION_FILTER_ALL) {
            if (mode == MODE.ADD) {
                preference.setAlarm(audioManager.getStreamVolume(AudioManager.STREAM_ALARM));
                preference.setCall(audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
                preference.setMedia(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                preference.setNotification(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
                preference.setRingtone(audioManager.getStreamVolume(AudioManager.STREAM_RING));
            }

            if (profile.getMediaSelected())
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, profile.getMedia(), flag);
            if (profile.getNotificationSelected())
                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, profile.getNotification(), flag);
            if (profile.getCallSelected())
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, profile.getCall(), flag);
            if (profile.getAlarmSelected())
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, profile.getAlarm(), flag);
            if (profile.getRingtoneSelected())
                audioManager.setStreamVolume(AudioManager.STREAM_RING, profile.getRingtone(), flag);

        }
        preference.setId(profile.getId());
        new AsyncExecutor().execute(() -> repository.addVolumeSettings(preference));

    }

    public void changeState(Profile profile) {
        new AsyncExecutor().execute(() -> repository.updateProfileState(profile, ProfileExecutionState.RUNNING));
    }

}
