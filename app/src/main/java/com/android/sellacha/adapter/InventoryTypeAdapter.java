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
import com.android.sellacha.databinding.FilterNamesItemviewBinding;
import com.android.sellacha.api.model.inventoryTypeDM;

import java.util.ArrayList;

public class InventoryTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<inventoryTypeDM> list;

    public InventoryTypeAdapter(Context context, ArrayList<inventoryTypeDM> list) {
        this.context = context;
        this.list = list;
    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        FilterNamesItemviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.filter_names_itemview, group, false);
        return new InventoryTypeHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        InventoryTypeHolder mHolder = (InventoryTypeHolder) holder;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class InventoryTypeHolder extends RecyclerView.ViewHolder {
        FilterNamesItemviewBinding binding;

        public InventoryTypeHolder(FilterNamesItemviewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(ArrayList<inventoryTypeDM> inventoryTypeDMArrayList, int position) {
            binding.itemNameTv.setText(inventoryTypeDMArrayList.get(position).getTypeName());
            binding.count.setText(inventoryTypeDMArrayList.get(position).getCount());


        }
    }
}

