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
import com.android.sellacha.databinding.CustomerItemBinding;
import com.android.sellacha.api.model.inventoryDM;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<inventoryDM> list;

    public CustomerAdapter(Context context, ArrayList<inventoryDM> list) {
        this.context = context;
        this.list = list;
    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        CustomerItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.customer_item, group, false);
        return new CustomerAdapter.CustomerAdapterHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CustomerAdapter.CustomerAdapterHolder mHolder = (CustomerAdapter.CustomerAdapterHolder) holder;
        mHolder.setDataBind(list, position);
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

    public class CustomerAdapterHolder extends RecyclerView.ViewHolder {
        CustomerItemBinding binding;

        public CustomerAdapterHolder(CustomerItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(ArrayList<inventoryDM> attributeDMArrayList, int position) {
            binding.name.setText(attributeDMArrayList.get(position).getProductName());
            binding.email.setText(attributeDMArrayList.get(position).getSku());

        }
    }
}

