package com.android.sellacha.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.sellacha.R;
import com.android.sellacha.api.response.Product.DataItem;
import com.android.sellacha.databinding.LoadMoreItemBinding;
import com.android.sellacha.databinding.ProductItemBinding;
import com.android.sellacha.utils.AppProgressBar;
import com.android.sellacha.utils.TextUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    int currentPos;
    String typeName = "";
    List<DataItem> productList = new ArrayList<>();


    // for load more
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public ProductAdapter(Context context, List<DataItem> productList, int selected, RecyclerView recyclerView) {
        this.context = context;
        this.currentPos = selected;
        this.productList = productList;

        // load more
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return getViewHolder(LayoutInflater.from(context), parent);
        } else if (viewType == VIEW_TYPE_LOADING) {
            return getViewHolderLoad(LayoutInflater.from(context), parent);
        }
        return null;
    }


    private RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ProductItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_item, group, false);
        return new ProductAdapter.ProductHolder(binding);
    }

    private RecyclerView.ViewHolder getViewHolderLoad(LayoutInflater inflater, ViewGroup group) {
        LoadMoreItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.load_more_item, group, false);
        return new ProductAdapter.ViewHolderLoading(binding);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return productList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoaded() {
        isLoading = false;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof ProductHolder) {
            if (productList.get(position) != null) {
                ProductHolder productHolder = (ProductHolder) holder;
                productHolder.setDataBind(productList.get(position), position);
                productHolder.binding.mainLayout.setOnClickListener(view -> {
                    //    Navigation.findNavController(mHolder.binding.getRoot()).navigate(R.id.orderDetailsFragment);
                });
            }

        } else if (holder instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holder;
            loadingViewHolder.binding.itemProgressbar.setIndeterminate(true);
        }

        if (position == productList.size() - 1) {
            AppProgressBar.hideLoaderDialog();
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void notifySelection(List<DataItem> orderList, String status) {
        List<DataItem> tempList = new ArrayList<>();
        for (int x = 0; x < orderList.size(); x++) {
            if (orderList.get(x).getStatus().equalsIgnoreCase(status)) {
                tempList.add(orderList.get(x));
            }
            this.productList = tempList;
        }
        notifyDataSetChanged();
    }

    public void filteredList(ArrayList<DataItem> filteredPlacesArrayList) {
        productList = filteredPlacesArrayList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolderLoading extends RecyclerView.ViewHolder {
        LoadMoreItemBinding binding;

        public ViewHolderLoading(LoadMoreItemBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }


    public class ProductHolder extends RecyclerView.ViewHolder {
        ProductItemBinding binding;

        public ProductHolder(ProductItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        void setDataBind(DataItem name, int position) {
            binding.name.setText(TextUtils.getString(name.getTitle().toString()));
            if (name.getPreview() != null) {
                Glide.with(context).load(TextUtils.getString(name.getPreview().getMedia().getUrl()))
                        .placeholder(R.drawable.app_logo).error(R.drawable.ic_baseline_no_photography_24)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.orderName);
            }
            if ((TextUtils.getString(name.getStatus()).equalsIgnoreCase("1"))) {
                binding.payment.setBackgroundResource(R.drawable.green_10_bg);
                binding.payment.setText("Active");
            } else if ((TextUtils.getString(name.getStatus()).equalsIgnoreCase("2"))) {
                binding.payment.setBackgroundResource(R.drawable.yellow_10_bg);
                //  binding.payment.setPadding(0, 0, 0, 0);
                binding.payment.setText("Draft");
            } else if ((TextUtils.getString(name.getStatus()).equalsIgnoreCase("3"))) {
                binding.payment.setBackgroundResource(R.drawable.yellow_10_bg);
                //  binding.payment.setPadding(0, 0, 0, 0);
                binding.payment.setText("Incomplete");
            } else if ((TextUtils.getString(name.getStatus()).equalsIgnoreCase("0"))) {
                binding.payment.setBackgroundResource(R.drawable.yellow_10_bg);
                //  binding.payment.setPadding(0, 0, 0, 0);"
                binding.payment.setText("Trash");
            }

            binding.totalSale.setText(TextUtils.getString(name.getOrderCount().toString()));
//            String date = new SimpleDateFormat().format(name.getUpdatedAt());
            binding.lastUpdate.setText(name.getFormate_date());
        }
    }
}

