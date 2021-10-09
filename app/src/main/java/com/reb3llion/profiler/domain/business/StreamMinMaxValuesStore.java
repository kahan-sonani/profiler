package com.reb3llion.profiler.domain.business;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;

import androidx.preference.PreferenceManager;

import com.reb3llion.profiler.R;
import com.reb3llion.profiler.presenter.ProfilerApplication;

public class StreamMinMaxValuesStore {

    public static final String MEDIA_MIN = "MEDIA_MIN";
    public static final String MEDIA_MAX = "MEDIA_MAX";
    public static final String NOTIFICATION_MIN = "NOTIFICATION_MIN";
    public static final String NOTIFICATION_MAX = "NOTIFICATION_MAX";
    public static final String ALARM_MIN = "ALARM_MIN";
    public static final String ALARM_MAX = "ALARM_MAX";
    public static final String CALL_MIN = "CALL_MIN";
    public static final String CALL_MAX = "CALL_MAX";
    public static final String RINGTONE_MIN = "RINGTONE_MIN";
    public static final String RINGTONE_MAX = "RINGTONE_MAX";
    private static final String INITIALIZED = "maxStreamsInitialized";
    private final SharedPreferences preferences;

    public StreamMinMaxValuesStore() {
        Context context = ProfilerApplication.getAppContext();
        preferences = context.getSharedPreferences(context.getString(R.string.max_stream_volume), Context.MODE_PRIVATE);
    }

    public static void init() {
        Context context = ProfilerApplication.getAppContext();
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(context);
        if (!preferences1.getBoolean(INITIALIZED, false)) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            SharedPreferences preferences2 = context.getSharedPreferences(context.getString(R.string.max_stream_volume), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putInt(MEDIA_MAX, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            editor.putInt(NOTIFICATION_MAX, audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
            editor.putInt(RINGTONE_MAX, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
            editor.putInt(ALARM_MAX, audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));
            editor.putInt(CALL_MAX, audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
            editor.apply();
            preferences1.edit().putBoolean(INITIALIZED, true).apply();
        }
    }

    public int getMediaMax() {
        return preferences.getInt(MEDIA_MAX, 15);
    }

    public int getNotificationMax() {
        return preferences.getInt(NOTIFICATION_MAX, 7);
    }

    public int getAlarmMax() {
        return preferences.getInt(ALARM_MAX, 7);
    }

    public int getRingtoneMax() {
        return preferences.getInt(RINGTONE_MAX, 15);
    }

    public int getCallMax() {
        return preferences.getInt(CALL_MAX, 5);
    }

}
