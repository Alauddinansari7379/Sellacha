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
import com.android.sellacha.databinding.AttributesItemsBinding;
import com.android.sellacha.api.model.attributeDM;

import java.util.ArrayList;


public class AttributeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<attributeDM> list;

    public AttributeAdapter(Context context, ArrayList<attributeDM> list) {
        this.context = context;
        this.list = list;
    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        AttributesItemsBinding binding = DataBindingUtil.inflate(inflater, R.layout.attributes_items, group, false);
        return new AttributeHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AttributeHolder mHolder = (AttributeHolder) holder;
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

    public class AttributeHolder extends RecyclerView.ViewHolder {
        AttributesItemsBinding binding;

        public AttributeHolder(AttributesItemsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(ArrayList<attributeDM> attributeDMArrayList, int position) {
            binding.nameTv.setText(attributeDMArrayList.get(position).getName());
            binding.varitionsTv.setText(attributeDMArrayList.get(position).getVaraitions());
            binding.attributeRbtn.setSelected(attributeDMArrayList.get(position).getSelected());

        }
    }
}

