package com.reb3llion.profiler.presenter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.reb3llion.profiler.databinding.HelpFragmentBinding;

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
