package com.reb3llion.profiler.presenter.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.reb3llion.profiler.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_fragment, rootKey);
    }

}
