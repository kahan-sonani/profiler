package com.reb3llion.profiler.presenter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.databinding.ProfilerListItemBinding;
import com.reb3llion.profiler.domain.business.PermissionManager;
import com.reb3llion.profiler.domain.business.ProfileExecutionState;
import com.reb3llion.profiler.domain.business.ProfileManagement;
import com.reb3llion.profiler.domain.business.TimeFormat;

import java.util.Objects;

public class ProfileListAdapter extends ListAdapter<Profile, ProfileListAdapter.ProfileViewHolder> {

    private ProfileAdapterInteractor interactor;
    private boolean dndPermissionNotGranted;

    public ProfileListAdapter() {
        super(DIFF_UTIL_CALLBACK);
        dndPermissionNotGranted = PermissionManager.isDNDPermissionNotGranted();
    }

    public void setInteractor(ProfileAdapterInteractor interactor) {
        this.interactor = interactor;
    }

    private static final DiffUtil.ItemCallback<Profile> DIFF_UTIL_CALLBACK = new DiffUtil.ItemCallback<Profile>() {

        @Override
        public boolean areItemsTheSame(@NonNull Profile oldItem, @NonNull Profile newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Profile oldItem, @NonNull Profile newItem) {
            return ProfileManagement.areBothProfileSame(newItem, oldItem);
        }
    };

    public Profile getProfileAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileViewHolder(ProfilerListItemBinding.inflate(
                Objects.requireNonNull(LayoutInflater.from(parent.getContext()))));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {

        Profile tuple = getItem(position);
        holder.binding.profileLabel.setText(tuple.getLabel());
        holder.binding.days.setText(ProfileManagement.getDayStringFromProfile(tuple));
        String s = TimeFormat.getTimeStringWithTypography(tuple.getStartTime());
        String e = TimeFormat.getTimeStringWithTypography(tuple.getEndTime());
        String[] splits = TimeFormat.splitTypographyAndTime(s);
        String[] splite = TimeFormat.splitTypographyAndTime(e);
        holder.binding.startTimeList.setText(splits[0]);
        holder.binding.endTimeList.setText(splite[0]);
        holder.binding.profileEnable.setChecked(tuple.getEnable());
        holder.binding.amPmStart.setText(splits[1]);
        holder.binding.amPmEnd.setText(splite[1]);
        boolean value = ProfileManagement.doesProfileNeedDNDPermission(tuple) &&
                dndPermissionNotGranted;
        holder.binding.dndAlert.setVisibility(value ? View.VISIBLE : View.GONE);
        holder.binding.profileEnable.setVisibility(value ? View.GONE : View.VISIBLE);
        holder.binding.stateTick.setVisibility(tuple.getState().getInt() == ProfileExecutionState.RUNNING ? View.VISIBLE : View.GONE);
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        private final ProfilerListItemBinding binding;

        public ProfileViewHolder(ProfilerListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(v -> interactor.onClick(this.getBindingAdapterPosition()));
            this.binding.profileEnable.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (compoundButton.isPressed()) {
                    Profile profile = getProfileAt(getBindingAdapterPosition());
                    interactor.onEnableDisableProfile(profile, !profile.getEnable());
                }
            });
        }
    }

    public void setDndPermissionNotGranted(boolean dndPermissionNotGranted) {
        this.dndPermissionNotGranted = dndPermissionNotGranted;
    }
}
