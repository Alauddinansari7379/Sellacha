package com.android.sellacha.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.sellacha.R;
import com.android.sellacha.api.model.filterItemsDM;
import com.android.sellacha.databinding.FilterNamesItemviewBinding;
import com.android.sellacha.Products.ProductFragment;

public class ProductFilterSelector extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    int currentPos = -1;
    String typeName = "";
    filterItemsDM[] list;
    ProductFragment productFragment;

    public ProductFilterSelector(Context context, filterItemsDM[] list, int selected, ProductFragment productFragment) {
        this.context = context;
        this.list = list;
        this.currentPos = selected;
        this.productFragment = productFragment;

    }

    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        FilterNamesItemviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.filter_names_itemview, group, false);
        return new ProductFilterSelector.FilterSelectorHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context), parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductFilterSelector.FilterSelectorHolder billsHolder = (ProductFilterSelector.FilterSelectorHolder) holder;
        billsHolder.setDataBind(list[position], position);

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class FilterSelectorHolder extends RecyclerView.ViewHolder {
        FilterNamesItemviewBinding binding;

        public FilterSelectorHolder(FilterNamesItemviewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(filterItemsDM itemsDM, int position) {
            binding.itemNameTv.setText(itemsDM.getFilterType());
            binding.count.setText(String.valueOf(itemsDM.getFilterTypeQty()));
            if (currentPos == position) {
                binding.mainLayout.setBackgroundResource(R.drawable.selected_item);
                binding.itemNameTv.setTextColor(Color.WHITE);
            } else {
                binding.mainLayout.setBackgroundResource(R.drawable.unselected_item_bg);
                binding.itemNameTv.setTextColor(context.getColor(R.color._6E7D86));
            }
            binding.mainLayout.setOnClickListener(view -> {
                currentPos = position;
                productFragment.filterSelector(position);
                notifyDataSetChanged();
            });
        }
    }
}
