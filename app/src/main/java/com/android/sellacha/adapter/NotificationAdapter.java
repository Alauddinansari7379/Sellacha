package com.android.sellacha.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.sellacha.R;
import com.android.sellacha.databinding.NotificationItemBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<NotificationResponse> notificationList;

    public NotificationAdapter(Context context, List<NotificationResponse> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        NotificationItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.notification_item, group, false);
        return new NotificationHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NotificationAdapter.NotificationHolder vHolder = (NotificationAdapter.NotificationHolder) holder;
        vHolder.binding.notificationTxt.setText(notificationList.get(position).getMessage());
        vHolder.binding.notificationTime.setText(notificationList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class NotificationHolder extends RecyclerView.ViewHolder {
        NotificationItemBinding binding;

        public NotificationHolder(NotificationItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }
}