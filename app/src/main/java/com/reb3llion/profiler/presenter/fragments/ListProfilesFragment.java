 package com.reb3llion.profiler.presenter.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.reb3llion.profiler.R;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.databinding.ListProfilesFragmentBinding;
import com.reb3llion.profiler.presenter.adapters.DNDPermissionAdapter;
import com.reb3llion.profiler.presenter.adapters.ProfileAdapterInteractor;
import com.reb3llion.profiler.presenter.adapters.ProfileListAdapter;
import com.reb3llion.profiler.presenter.enums.MODE;
import com.reb3llion.profiler.presenter.models.ListProfileFragmentModel;

public class ListProfilesFragment extends Fragment {

    private ListProfilesFragmentBinding binding;
    private ListProfileFragmentModel model;
    private DNDPermissionAdapter dndPermissionAdapter;
    private ProfileListAdapter profileListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(ListProfileFragmentModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ListProfilesFragmentBinding.inflate(inflater);
        model.onProfileDeleteStatus.observe(getViewLifecycleOwner(), status -> Snackbar.make(binding.getRoot(), status.getMessage(requireContext()),
                BaseTransientBottomBar.LENGTH_LONG)
                .setAnchorView(requireActivity().findViewById(R.id.bottom_navigation))
                .show());
        return binding.getRoot();
    }

    @SuppressLint("InlinedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(dndPermissionAdapter == null && profileListAdapter == null) {
            dndPermissionAdapter = new DNDPermissionAdapter(() -> {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
            });
            profileListAdapter = new ProfileListAdapter();
            profileListAdapter.setInteractor(new ProfileAdapterInteractor() {
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
        }

        ConcatAdapter concatAdapter = new ConcatAdapter();
        concatAdapter.addAdapter(dndPermissionAdapter);
        concatAdapter.addAdapter(profileListAdapter);

        binding.profileList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.profileList.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.profileList.setAdapter(concatAdapter);
        binding.profileList.setHasFixedSize(true);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(viewHolder.getItemViewType() == DNDPermissionAdapter.TYPE)
                    concatAdapter.notifyItemChanged(0);
                else {
                    new MaterialAlertDialogBuilder(requireContext())
                            .setTitle(R.string.warning)
                            .setCancelable(false)
                            .setMessage(R.string.delete_profile_question)
                            .setPositiveButton(R.string.yes, (dialog, which) -> {
                                model.deleteProfile(profileListAdapter.getProfileAt(viewHolder.getBindingAdapterPosition()));
                            })
                            .setNegativeButton(R.string.no, (dialog, which) -> profileListAdapter.notifyItemChanged(viewHolder.getBindingAdapterPosition())).show();
                }
            }
        }).attachToRecyclerView(binding.profileList);

        model.dndPermissionRequired.observe(getViewLifecycleOwner(), aBoolean -> {
            dndPermissionAdapter.displayRationale(aBoolean);
            profileListAdapter.setDndPermissionNotGranted(aBoolean);
            dndPermissionAdapter.notifyDataSetChanged();

        });
        model.getAllProfiles().observe(getViewLifecycleOwner(), profileTuples -> {
            boolean empty = profileTuples.isEmpty();
            binding.listEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
            profileListAdapter.submitList(profileTuples);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        model.checkForDNDPermission();
    }
}
