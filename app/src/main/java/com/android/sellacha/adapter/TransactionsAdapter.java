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
import com.android.sellacha.databinding.TransationItemBinding;
import com.android.sellacha.api.model.transactionsDM;

import java.util.ArrayList;


public class TransactionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<transactionsDM> list;

    public TransactionsAdapter(Context context, ArrayList<transactionsDM> list) {
        this.context = context;
        this.list = list;
    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        TransationItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.transation_item, group, false);
        return new TransactionsHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TransactionsHolder mHolder = (TransactionsHolder) holder;
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

    public class TransactionsHolder extends RecyclerView.ViewHolder {
        TransationItemBinding binding;

        public TransactionsHolder(TransationItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(ArrayList<transactionsDM> transactionsDMArrayList, int position) {
            binding.amt.setText(String.valueOf(transactionsDMArrayList.get(position).getAmount()));
            binding.paymentStatus.setText(transactionsDMArrayList.get(position).getPayment());
            binding.payMethod.setText(transactionsDMArrayList.get(position).getMethod());
            binding.orderNum.setText(transactionsDMArrayList.get(position).getOrederno());
            binding.tansNum.setText(transactionsDMArrayList.get(position).getTransactionId());

        }
    }
}

