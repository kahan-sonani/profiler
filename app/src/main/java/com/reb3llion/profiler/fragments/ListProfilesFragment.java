package com.reb3llion.profiler.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.reb3llion.profiler.R;
import com.reb3llion.profiler.adapters.ProfileListAdapter;
import com.reb3llion.profiler.databinding.ListProfilesFragmentBinding;
import com.reb3llion.profiler.models.ListProfileFragmentModel;
import com.reb3llion.profiler.room.entities.Profile;
import com.reb3llion.profiler.utils.MODE;
import com.reb3llion.profiler.utils.ProfileAdapterInteractor;

public class ListProfilesFragment extends Fragment {

    private ListProfilesFragmentBinding binding;
    private ListProfileFragmentModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        model = new ViewModelProvider(requireActivity()).get(ListProfileFragmentModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ListProfilesFragmentBinding.inflate(inflater, container, false);
        model.onProfileDeleteStatus.observe(getViewLifecycleOwner(), status -> Snackbar.make(binding.getRoot(), status.getMessage(requireContext()),
                BaseTransientBottomBar.LENGTH_LONG)
                .setAnchorView(requireActivity().findViewById(R.id.bottom_navigation))
                .show());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProfileListAdapter adapter = new ProfileListAdapter();
        binding.profileList.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.profileList.setAdapter(adapter);
        binding.profileList.setHasFixedSize(true);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(requireContext())
                        .setTitle(R.string.warning)
                        .setCancelable(false)
                        .setMessage(R.string.delete_profile_question)
                        .setPositiveButton(R.string.yes, (dialog, which) -> model.deleteProfile(adapter.getProfileAt(viewHolder.getAbsoluteAdapterPosition())))
                        .setNegativeButton(R.string.no, (dialog, which) -> adapter.notifyItemChanged(viewHolder.getAbsoluteAdapterPosition())).show();

            }
        }).attachToRecyclerView(binding.profileList);
        adapter.setInteractor(new ProfileAdapterInteractor() {
            @Override
            public void onClick(int index) {
                ListProfilesFragmentDirections.ActionMenuShowListToMenuAddProfile actionMenuShowListToMenuAddProfile
                        = ListProfilesFragmentDirections.actionMenuShowListToMenuAddProfile();
                actionMenuShowListToMenuAddProfile.setMode(MODE.UPDATE.getValue());
                actionMenuShowListToMenuAddProfile.setProfileIndex(index);
                NavHostFragment.findNavController(ListProfilesFragment.this)
                        .navigate(actionMenuShowListToMenuAddProfile);
            }

            @Override
            public void onEnableDisableProfile(Profile profile, boolean update) {
                model.enableDisableProfile(profile, update);
            }
        });

        model.profileList.observe(getViewLifecycleOwner(), profileTuples -> {
            Log.i("list11", "called");
            boolean empty = profileTuples.isEmpty();
            binding.listEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
            adapter.submitList(profileTuples);
        });
    }
}
