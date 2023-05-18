package com.android.sellacha.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.sellacha.R;
import com.android.sellacha.api.response.GetAllorder.OrderItem;
import com.android.sellacha.databinding.OrderDetailsItemBinding;
import com.android.sellacha.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    int currentPos = -1;
    String typeName = "";
    List<OrderItem> orderList = new ArrayList<>();

    public OrderAdapter(Context context, List<OrderItem> orderList, int selected) {
        this.context = context;
        this.orderList = orderList;
        this.currentPos = selected;

    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        OrderDetailsItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_details_item, group, false);
        return new OrderAdapter.OrderHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OrderAdapter.OrderHolder mHolder = (OrderAdapter.OrderHolder) holder;
        OrderItem orders = orderList.get(position);
        mHolder.setDataBind(orders, position);
        mHolder.binding.orderName.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("orderID", orders.getId());
            Navigation.findNavController(mHolder.binding.getRoot()).navigate(R.id.orderDetailsFragment, bundle);
        });
        mHolder.binding.date.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("orderID", orders.getId());
            Navigation.findNavController(mHolder.binding.getRoot()).navigate(R.id.orderDetailsFragment, bundle);
        });
        mHolder.binding.printInvoice.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://thedemostore.in/seller/orders/invoicenew/21/22"));
            context.startActivity(browserIntent);
        });
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

//    public void searchNotify(String search) {
//        List<OrderItem> templist = new ArrayList<>();
//        for (int x = 0; x < orderList.size(); x++) {
//            if ( orderList.get(0).getOrderNo().toLowerCase().contains(search.toLowerCase())){
//                templist.add(orderList.get(x));
//            }
//        }
//        orderList = templist;
//        notifyDataSetChanged();
//    }
    public void filteredList(ArrayList<OrderItem> filteredPlacesArrayList) {
        orderList = filteredPlacesArrayList;
        notifyDataSetChanged();
    }
    public boolean containsChar(String s, char search) {
        if (s.length() > 5) throw new IllegalArgumentException();

        try {
            if (s.charAt(0) == search) return true;
            if (s.charAt(1) == search) return true;
            if (s.charAt(2) == search) return true;
            if (s.charAt(3) == search) return true;
            if (s.charAt(4) == search) return true;
        } catch (IndexOutOfBoundsException e) {
            // this should never happen...
            return false;
        }
        return false;
    }

    public void notifySelection(List<OrderItem> orderList, String status) {
        List<OrderItem> tempList = new ArrayList<>();
        for (int x = 0; x < orderList.size(); x++) {
            if (orderList.get(x).getStatus().equalsIgnoreCase(status)) {
                tempList.add(orderList.get(x));
            }
            this.orderList = tempList;
        }
        notifyDataSetChanged();
    }


    public class OrderHolder extends RecyclerView.ViewHolder {
        OrderDetailsItemBinding binding;

        public OrderHolder(OrderDetailsItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(@NonNull OrderItem orders, int position) {
            binding.orderName.setText(TextUtils.getString(orders.getOrderNo().toString()));
            binding.date.setText(TextUtils.getString(orders.getSDate()));
            if (orders.getCustomer() != null) {
                binding.customer.setText(TextUtils.getString(orders.getCustomer().getName()));
            }

            if (TextUtils.getString(orders.getStatus()).equals("completed")) {
                binding.Fulfillment.setText("Competed");
                binding.Fulfillment.setBackgroundResource(R.drawable.green_10_bg);
            } else if (TextUtils.getString(orders.getStatus()).equals("pending")) {
                binding.Fulfillment.setText("Pending");
                binding.Fulfillment.setBackgroundResource(R.drawable.yellow_10_bg);
            }

//            } else if (TextUtils.getString(orders.getStatus()).equals("3")) {
//                binding.Fulfillment.setText("Awaiting processing");
//                binding.Fulfillment.setBackgroundResource(R.drawable.red_10bg);
//            }

            binding.orderTotal.setText(TextUtils.getString(orders.getTotal().toString()));
            binding.items.setText(TextUtils.getString(orders.getOrderItemsCount().toString()));

            if ((TextUtils.getString(orders.getPaymentStatus()).equalsIgnoreCase("1"))) {
                binding.payment.setBackgroundResource(R.drawable.green_10_bg);
                binding.payment.setText("Completed");

            } else if ((TextUtils.getString(orders.getPaymentStatus()).equalsIgnoreCase("2"))) {
                binding.payment.setBackgroundResource(R.drawable.yellow_10_bg);
                //  binding.payment.setPadding(0, 0, 0, 0);
                binding.payment.setText("Pending");
            }

            if (currentPos == position) {
                binding.orderRbtn.setChecked(true);
            } else {
                binding.orderRbtn.setChecked(false);
            }
            binding.orderRbtn.setOnClickListener(view -> {
                currentPos = position;
                notifyDataSetChanged();
            });


//            if ((orders.getData().get(position).equals("completed")))
//            {
//                binding.payment.setBackgroundResource(R.drawable.green_10_bg);
//            }
//            else if((orders.getData().get(position).equals("Awaiting processing")))
//            {
//                binding.payment.setBackgroundResource(R.drawable.yellow_10_bg);
//            }
        }
    }
}


