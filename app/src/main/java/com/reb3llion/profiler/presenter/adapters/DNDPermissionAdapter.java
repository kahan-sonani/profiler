package com.reb3llion.profiler.presenter.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reb3llion.profiler.databinding.DndPermissionRationLayoutBinding;
import com.reb3llion.profiler.domain.PermissionManager;

public class DNDPermissionAdapter extends RecyclerView.Adapter<DNDPermissionAdapter.DNDPermissionViewHolder> {

    private int count;
    public static final int TYPE = 0;

    private DNDPermissionAdapterInteractor interactor;

    public DNDPermissionAdapter(DNDPermissionAdapterInteractor interactor){
        this.interactor = interactor;
        count = PermissionManager.isDNDPermissionNotGranted() ? 1 : 0;
    }
    @NonNull
    @Override
    public DNDPermissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DNDPermissionViewHolder(DndPermissionRationLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull DNDPermissionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class DNDPermissionViewHolder extends RecyclerView.ViewHolder{
        private DndPermissionRationLayoutBinding binding;

        public DNDPermissionViewHolder(DndPermissionRationLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.settings.setOnClickListener(v -> interactor.gotoSettings());
        }
    }

    public void displayRationale(boolean value) {
        count = value ? 1 : 0;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE;
    }
}
