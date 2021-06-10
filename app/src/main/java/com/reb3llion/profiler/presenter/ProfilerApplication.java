package com.reb3llion.profiler.presenter;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.reb3llion.profiler.utils.ThemeModeProvider;

public class ProfilerApplication extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        AppCompatDelegate.setDefaultNightMode(
                ThemeModeProvider.getSelectedThemeFromPreference(this));

    }

    public static Context getAppContext(){
        return application.getApplicationContext();
    }
}
