package com.reb3llion.profiler.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.reb3llion.profiler.databinding.ProfilerListItemBinding;
import com.reb3llion.profiler.models.ListProfileFragmentModel;
import com.reb3llion.profiler.room.entities.Profile;
import com.reb3llion.profiler.utils.ProfileAdapterInteractor;

import java.util.Objects;

public class ProfileListAdapter extends ListAdapter<Profile, ProfileListAdapter.ProfileViewHolder> {

    private static final String TAG = "profilerListAdapter";
    private ProfileAdapterInteractor interactor;

    public ProfileListAdapter() {
        super(DIFF_UTIL_CALLBACK);
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
            boolean v = oldItem.areBothContentSame(newItem);
            Log.i("list11", v + "");
            return v;
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
        holder.binding.startTimeList.setText(tuple.getStartTimeString());
        holder.binding.endTimeList.setText(tuple.getEndTimeString());
        holder.binding.profileEnable.setChecked(tuple.getEnable());
        holder.binding.profileEnable.setOnCheckedChangeListener((buttonView, isChecked) ->{
            Log.i("list11", tuple.getStartTimeString());
            interactor.onEnableDisableProfile(tuple, isChecked);
        });
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        private ProfilerListItemBinding binding;

        public ProfileViewHolder(ProfilerListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(v -> interactor.onClick(getAbsoluteAdapterPosition()));

        }
    }
}
