package com.reb3llion.profiler.presenter;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.reb3llion.profiler.domain.business.StreamMinMaxValuesStore;
import com.reb3llion.profiler.presenter.notification.ProfilerNotification;
import com.reb3llion.profiler.utils.ThemeModeProvider;

public class ProfilerApplication extends Application {

    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static Context getAppContext() {
        return application.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        AppCompatDelegate.setDefaultNightMode(
                ThemeModeProvider.getSelectedThemeFromPreference(this));
        ProfilerNotification.createNotificationChannel();
        StreamMinMaxValuesStore.init();

    }
}
