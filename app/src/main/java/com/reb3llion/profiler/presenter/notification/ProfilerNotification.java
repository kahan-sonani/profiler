package com.reb3llion.profiler.presenter.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.reb3llion.profiler.R;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.business.TimeFormat;
import com.reb3llion.profiler.presenter.ProfilerApplication;

public class ProfilerNotification {

    public static final int NOTIFICATION_ID = 1;
    public static final int ERROR_NOTIFICATION_ID = 2;
    private static final String CHANNEL_ID = "Profiler.Events";

    public static Notification buildNotification(Profile profile) {
        return new NotificationCompat.Builder(ProfilerApplication.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentTitle(ProfilerApplication.getAppContext().getString(R.string.app_name))
                .setContentText(getString(profile))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static Notification buildErrorNotification(Profile profile) {
        PendingIntent intent = PendingIntent.getActivity(ProfilerApplication.getAppContext(),
                0, new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                , PendingIntent.FLAG_ONE_SHOT);

        String errorString = getErrorString(profile);
        return new NotificationCompat.Builder(ProfilerApplication.getAppContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentText(errorString)
                .setContentTitle(ProfilerApplication.getAppContext().getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(errorString))
                .setContentIntent(intent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }

    private static String getString(Profile profile) {
        return (profile.getLabel().equals(Profile.PLACEHOLDER_LABEL)
                ? "" : profile.getLabel() + " ") + "Profile started from "
                + TimeFormat.getTimeStringWithTypography(profile.getStartTime())
                + " till " + TimeFormat.getTimeStringWithTypography(profile.getEndTime());
    }

    private static String getErrorString(Profile profile) {
        return ProfilerApplication.getAppContext().getString(R.string.permission_denied_message) + " to run "
                + (profile.getLabel().equals(Profile.PLACEHOLDER_LABEL)
                ? "" : profile.getLabel() + " ") + "profile";
    }

    public static void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ProfilerApplication.getAppContext().getString(R.string.channel_name);
            String description = ProfilerApplication.getAppContext().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = ProfilerApplication.getAppContext()
                    .getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
