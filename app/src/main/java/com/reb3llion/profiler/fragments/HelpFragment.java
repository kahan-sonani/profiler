package com.reb3llion.profiler.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.reb3llion.profiler.R;
import com.reb3llion.profiler.databinding.HelpFragmentBinding;
import com.reb3llion.profiler.utils.ProfilerDialogFragment;

public class HelpFragment extends BottomSheetDialogFragment {

    public static final String TAG = "com.reb3llion.HelpFragmemt";
    private  HelpFragmentBinding binding;

    public HelpFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HelpFragmentBinding binding = HelpFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
