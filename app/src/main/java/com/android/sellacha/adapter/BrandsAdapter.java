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
import com.android.sellacha.databinding.BransItemBinding;
import com.android.sellacha.api.model.reviewRatingDM;

import java.util.ArrayList;

public class BrandsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<reviewRatingDM> list;

    public BrandsAdapter(Context context, ArrayList<reviewRatingDM> list) {
        this.context = context;
        this.list = list;
    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        BransItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.brans_item, group, false);
        return new BrandsAdapter.BrandsAdapterHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BrandsAdapter.BrandsAdapterHolder mHolder = (BrandsAdapter.BrandsAdapterHolder) holder;
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

    public class BrandsAdapterHolder extends RecyclerView.ViewHolder {
        BransItemBinding binding;

        public BrandsAdapterHolder(BransItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(ArrayList<reviewRatingDM> reviewRatingDMArrayList, int position) {
            binding.name.setText(reviewRatingDMArrayList.get(position).getNames());
        }
    }
}

