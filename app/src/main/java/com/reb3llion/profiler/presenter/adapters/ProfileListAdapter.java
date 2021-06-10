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
import com.reb3llion.profiler.domain.PermissionManager;
import com.reb3llion.profiler.domain.TimeFormat;
import com.reb3llion.profiler.domain.business.ProfileManagement;

import java.util.Objects;

public class ProfileListAdapter extends ListAdapter<Profile, ProfileListAdapter.ProfileViewHolder> {

    private ProfileAdapterInteractor interactor;
    private boolean dndPermissionNotGranted;

    public static final int TYPE = 1;

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

        StringBuilder builder = new StringBuilder();
        Profile tuple = getItem(position);
        if (tuple.getSun()) {
            builder.append(", ");
            builder.append(Profile.SUN);
        }
        if (tuple.getMon()) {
            builder.append(", ");
            builder.append(Profile.MON);
        }
        if (tuple.getTue()) {
            builder.append(", ");
            builder.append(Profile.TUE);
        }
        if (tuple.getWed()) {
            builder.append(", ");
            builder.append(Profile.WED);
        }
        if (tuple.getThu()) {
            builder.append(", ");
            builder.append(Profile.THU);
        }
        if (tuple.getFri()) {
            builder.append(", ");
            builder.append(Profile.FRI);
        }
        if (tuple.getSat()) {
            builder.append(", ");
            builder.append(Profile.SAT);
        }
        builder.delete(0, 2);
        holder.binding.days.setText(builder.toString());
        String s = TimeFormat.getTimeStringWithTypography(tuple.getStartTime());
        String e = TimeFormat.getTimeStringWithTypography(tuple.getEndTime());
        String[] splits = TimeFormat.splitTypographyAndTime(s);
        String[] splite = TimeFormat.splitTypographyAndTime(e);
        holder.binding.startTimeList.setText(splits[0]);
        holder.binding.endTimeList.setText(splite[0]);
        holder.binding.profileEnable.setChecked(tuple.getEnable());
        holder.binding.amPmStart.setText(splits[1]);
        holder.binding.amPmEnd.setText(splite[1]);
        holder.binding.dndAlert.setVisibility(ProfileManagement.doesProfileNeedDNDPermission(tuple) &&
                dndPermissionNotGranted ? View.VISIBLE : View.GONE);
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        private ProfilerListItemBinding binding;

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

    @Override
    public int getItemViewType(int position) {
        return TYPE;
    }

    public void setDndPermissionNotGranted(boolean dndPermissionNotGranted) {
        this.dndPermissionNotGranted = dndPermissionNotGranted;
    }
}
