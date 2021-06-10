package com.reb3llion.profiler.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.reb3llion.profiler.R;

public class ThemeModeProvider {

    public static int getSelectedThemeFromPreference(Context context){
        String theme = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_theme_selected), "");
        return "".compareTo(theme) == 0 ? getDefaultTheme() : getThemeMode(theme, context);
    }
    public static int getDefaultTheme(){
        return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
    }

    public static int getThemeMode(String mode, Context context){
        String[] modes = context.getResources().getStringArray(R.array.themes_values);
        if(mode.compareTo(modes[0]) == 0)
            return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        else if(mode.compareTo(modes[1]) == 0)
            return AppCompatDelegate.MODE_NIGHT_NO;
        else if(mode.compareTo(modes[2]) == 0)
            return AppCompatDelegate.MODE_NIGHT_YES;
        else if(mode.compareTo(modes[3]) == 0)
            return AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY;
        return getDefaultTheme();
    }
}
