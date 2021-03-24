package com.reb3llion.profiler.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.reb3llion.profiler.R;
import com.reb3llion.profiler.utils.VolumeItemsDataDump;

import java.util.Objects;

public class AddOrEditProfileFragment extends Fragment {

    private LinearLayout[] viewSettings;
    private View root;
    public AddOrEditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_edit_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewSettings = new LinearLayout[VolumeItemsDataDump.size];
        viewSettings[0] = (LinearLayout) ((ViewStub) root.findViewById(R.id.view_stub1)).inflate();
        viewSettings[1] = (LinearLayout) ((ViewStub) root.findViewById(R.id.view_stub2)).inflate();
        viewSettings[2] = (LinearLayout) ((ViewStub) root.findViewById(R.id.view_stub3)).inflate();
        viewSettings[3] = (LinearLayout) ((ViewStub) root.findViewById(R.id.view_stub4)).inflate();
        viewSettings[4] = (LinearLayout) ((ViewStub) root.findViewById(R.id.view_stub5)).inflate();

        for(int i = 0; i < VolumeItemsDataDump.size; i++){
            ((ImageView) viewSettings[i].findViewById(R.id.icon)).setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),VolumeItemsDataDump.volumeItemIcons[i]));
        }
    }
}