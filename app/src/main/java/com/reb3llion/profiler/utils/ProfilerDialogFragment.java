package com.reb3llion.profiler.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.reb3llion.profiler.R;
import com.reb3llion.profiler.fragments.HelpFragment;

import java.util.Objects;

public abstract class ProfilerDialogFragment extends DialogFragment {

    public static ProfilerDialogFragment display(FragmentManager fragmentManager, Class<? extends ProfilerDialogFragment> instance){
        ProfilerDialogFragment fragment = null;
        try {
            fragment = instance.newInstance();
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(fragment).show(fragmentManager, instance.getCanonicalName());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Profiler_FullScreenDialogTheme);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
