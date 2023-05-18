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
import com.android.sellacha.databinding.ReviewItemsBinding;
import com.android.sellacha.api.model.reviewRatingDM;

import java.util.ArrayList;

public class ReviewRatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<reviewRatingDM> list;

    public ReviewRatingAdapter(Context context, ArrayList<reviewRatingDM> list) {
        this.context = context;
        this.list = list;
    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ReviewItemsBinding binding = DataBindingUtil.inflate(inflater, R.layout.review_items, group, false);
        return new ReviewRatingHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ReviewRatingHolder mHolder = (ReviewRatingHolder) holder;
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

    public class ReviewRatingHolder extends RecyclerView.ViewHolder {
        ReviewItemsBinding binding;

        public ReviewRatingHolder(ReviewItemsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(ArrayList<reviewRatingDM> reviewRatingDMArrayList, int position) {
            binding.ratingsTv.setText(reviewRatingDMArrayList.get(position).getRatings());
            binding.ratingNameTv.setText(reviewRatingDMArrayList.get(position).getNames());
            binding.ratingsRbtn.setSelected(reviewRatingDMArrayList.get(position).getRadioButton());

        }
    }
}

