package com.reb3llion.profiler.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.reb3llion.profiler.databinding.ListProfilesFragmentBinding;

public class ListProfilesFragment extends Fragment {

    private ListProfilesFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ListProfilesFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
