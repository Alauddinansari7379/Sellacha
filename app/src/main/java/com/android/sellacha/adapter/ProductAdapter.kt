package com.android.sellacha.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.R
import com.android.sellacha.api.response.Product.DataItem
import com.android.sellacha.databinding.LoadMoreItemBinding
import com.android.sellacha.databinding.ProductItemBinding
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.TextUtils
import com.squareup.picasso.Picasso

class ProductAdapter(
    var context: Context,
    productList: List<DataItem?>,
    var currentPos: Int,
    recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var typeName = ""
    var productList: List<DataItem?> = ArrayList()

    // for load more
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var onLoadMoreListener: OnLoadMoreListener? = null

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var isLoading = false
    private val visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount = 0

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    init {
        this.productList = productList

        // load more
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager!!.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener!!.onLoadMore()
                    }
                    isLoading = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            return getViewHolder(LayoutInflater.from(context), parent)
        } else if (viewType == VIEW_TYPE_LOADING) {
            return getViewHolderLoad(LayoutInflater.from(context), parent)
        }
        return getViewHolder(LayoutInflater.from(context), parent)
    }

    private fun getViewHolder(inflater: LayoutInflater, group: ViewGroup): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ProductItemBinding>(
            inflater,
            R.layout.product_item,
            group,
            false
        )
        return ProductHolder(binding)
    }

    private fun getViewHolderLoad(
        inflater: LayoutInflater,
        group: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<LoadMoreItemBinding>(
            inflater,
            R.layout.load_more_item,
            group,
            false
        )
        return ViewHolderLoading(binding)
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: () -> Any) {
        onLoadMoreListener
    }

    override fun getItemViewType(position: Int): Int {
        return if (productList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setLoaded() {
        isLoading = false
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        if (holder is ProductHolder) {
            if (productList[position] != null) {
                holder.setDataBind(productList[position], position)
                holder.binding.mainLayout.setOnClickListener { view: View? -> }
            }
        } else if (holder is ViewHolderLoading) {
            val loadingViewHolder = holder
            //  loadingViewHolder.binding.itemProgressbar.setIndeterminate(true);
        }
        if (position == productList.size - 1) {
            AppProgressBar.hideLoaderDialog()
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun notifySelection(orderList: List<DataItem>, status: String?) {
        val tempList: MutableList<DataItem?> = ArrayList()
        for (x in orderList.indices) {
            if (orderList[x].status.equals(status, ignoreCase = true)) {
                tempList.add(orderList[x])
            }
            productList = tempList
        }
        notifyDataSetChanged()
    }

    fun filteredList(filteredPlacesArrayList: java.util.ArrayList<DataItem>) {
        productList = filteredPlacesArrayList
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private inner class ViewHolderLoading(var binding: LoadMoreItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ProductHolder(var binding: ProductItemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        fun setDataBind(name: DataItem?, position: Int) {
            binding.name.text = TextUtils.getString(name!!.title.toString())
            if (name.preview != null) {
                Picasso.get().load("https:"+name.preview.media.url).into(binding.orderName)
             }
//            if (name.preview != null) {
//                Glide.with(context).load(TextUtils.getString(name.preview.media.url)).placeholder(R.drawable.app_logo).error(R.drawable.ic_baseline_no_photography_24)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.orderName)
//            }
            if (TextUtils.getString(name.status).equals("1", ignoreCase = true)) {
                binding.payment.setBackgroundResource(R.drawable.green_10_bg)
                binding.payment.text = "Active"
            } else if (TextUtils.getString(name.status).equals("2", ignoreCase = true)) {
                binding.payment.setBackgroundResource(R.drawable.yellow_10_bg)
                //  binding.payment.setPadding(0, 0, 0, 0);
                binding.payment.text = "Draft"
            } else if (TextUtils.getString(name.status).equals("3", ignoreCase = true)) {
                binding.payment.setBackgroundResource(R.drawable.yellow_10_bg)
                //  binding.payment.setPadding(0, 0, 0, 0);
                binding.payment.text = "Incomplete"
            } else if (TextUtils.getString(name.status).equals("0", ignoreCase = true)) {
                binding.payment.setBackgroundResource(R.drawable.yellow_10_bg)
                //  binding.payment.setPadding(0, 0, 0, 0);"
                binding.payment.text = "Trash"
            }
            binding.totalSale.text = TextUtils.getString(name.orderCount.toString())
            //            String date = new SimpleDateFormat().format(name.getUpdatedAt());
            binding.lastUpdate.text = name.formate_date
        }
    }
}