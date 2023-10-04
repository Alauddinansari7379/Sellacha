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
import com.android.sellacha.api.response.OrderByID.OrderItemItem;
import com.android.sellacha.databinding.OrderInfoItemBinding;
import com.android.sellacha.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    int currentPos = -1;
    String typeName = "";
    List<OrderItemItem> orderList = new ArrayList<>();

    public OrderInfoAdapter(Context context, List<OrderItemItem> orderList, int selected) {
        this.context = context;
        this.orderList = orderList;
        this.currentPos = selected;

    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        OrderInfoItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_info_item, group, false);
        return new OrderInfoAdapter.OrderHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OrderInfoAdapter.OrderHolder mHolder = (OrderInfoAdapter.OrderHolder) holder;
        OrderItemItem orders = orderList.get(position);
        mHolder.setDataBind(orders, position);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class OrderHolder extends RecyclerView.ViewHolder {
        OrderInfoItemBinding binding;

        public OrderHolder(OrderInfoItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(@NonNull OrderItemItem orders, int position) {
            binding.productName.setText(TextUtils.getString(orders.getTerm().getTitle()));
            binding.amount.setText(TextUtils.getString(orders.getAmount()));
              binding.total.setText(TextUtils.getString(orders.getAmount().toString() + " * " + orders.getQty().toString()));
        }
    }
}


