package com.android.sellacha.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.sellacha.R;
import com.android.sellacha.databinding.LocationItemBinding;
import com.android.sellacha.api.model.locationDM;

public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    int currentPos = -1;
    String typeName = "";
    locationDM[] list;

    public LocationAdapter(Context context, locationDM[] list, int selected) {
        this.context = context;
        this.list = list;
        this.currentPos = selected;

    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        LocationItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.location_item, group, false);
        return new LocationAdapter.LocationAdapterHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LocationAdapter.LocationAdapterHolder holder1 = (LocationAdapter.LocationAdapterHolder) holder;
        holder1.setDataBind(list[position], position);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class LocationAdapterHolder extends RecyclerView.ViewHolder {
        LocationItemBinding binding;

        public LocationAdapterHolder(LocationItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(locationDM itemsDM, int position) {
            binding.addressTv.setText(itemsDM.getLocationNames());
            if (currentPos == position) {
                binding.loactionRb.setChecked(true);
            } else {
                binding.loactionRb.setChecked(false);
            }
            binding.mainLayout.setOnClickListener(view -> {
                currentPos = position;
                notifyDataSetChanged();
            });
        }
    }
}
