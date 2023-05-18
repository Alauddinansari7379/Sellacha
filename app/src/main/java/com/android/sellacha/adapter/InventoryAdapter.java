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
import com.android.sellacha.databinding.InventoryItemBinding;
import com.android.sellacha.api.model.inventoryDM;

import java.util.ArrayList;


public class InventoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<inventoryDM> list;

    public InventoryAdapter(Context context,ArrayList<inventoryDM> list) {
        this.context = context;
        this.list = list;
    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        InventoryItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.inventory_item, group, false);
        return new InventoryHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        InventoryHolder mHolder = (InventoryHolder) holder;

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

    public class InventoryHolder extends RecyclerView.ViewHolder {
        InventoryItemBinding binding;

        public InventoryHolder(InventoryItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(ArrayList<inventoryDM> attributeDMArrayList, int position) {
            binding.categoryImg.setImageResource(attributeDMArrayList.get(position).getProductImage());
            binding.categoryTv.setText(attributeDMArrayList.get(position).getProductName());
            binding.skuTv.setText(attributeDMArrayList.get(position).getSku());
            binding.stockManageTv.setText(attributeDMArrayList.get(position).getStockManage());

        }
    }
}

