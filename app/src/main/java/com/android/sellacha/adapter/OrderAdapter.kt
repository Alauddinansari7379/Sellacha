package com.android.sellacha.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.R
import com.android.sellacha.api.response.GetAllorder.OrderItem
import com.android.sellacha.databinding.OrderDetailsItemBinding
import com.android.sellacha.utils.TextUtils

class OrderAdapter(var context: Context, orderList: List<OrderItem>, selected: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var currentPos = -1
    var typeName = ""
    var orderList: List<OrderItem> = ArrayList()

    init {
        this.orderList = orderList
        currentPos = selected
    }

    private fun getViewHolder(inflater: LayoutInflater, group: ViewGroup): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<OrderDetailsItemBinding>(
            inflater,
            R.layout.order_details_item,
            group,
            false
        )
        return OrderHolder(binding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(context), parent)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val mHolder = holder as OrderHolder
        val orders = orderList[position]
        mHolder.setDataBind(orders, position)
        mHolder.binding.orderName.setOnClickListener { view: View? ->
            val bundle = Bundle()
            bundle.putInt("orderID", orders.id)
            findNavController(mHolder.binding.root).navigate(R.id.orderDetailsFragment, bundle)
        }
        mHolder.binding.date.setOnClickListener { view: View? ->
            val bundle = Bundle()
            bundle.putInt("orderID", orders.id)
            findNavController(mHolder.binding.root).navigate(R.id.orderDetailsFragment, bundle)
        }
        mHolder.binding.printInvoice.setOnClickListener { view: View? ->
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://thedemostore.in/seller/orders/invoicenew/21/22")
            )
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
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
    fun filteredList(filteredPlacesArrayList: ArrayList<OrderItem>) {
        orderList = filteredPlacesArrayList
        notifyDataSetChanged()
    }

    fun containsChar(s: String, search: Char): Boolean {
        require(s.length <= 5)
        try {
            if (s[0] == search) return true
            if (s[1] == search) return true
            if (s[2] == search) return true
            if (s[3] == search) return true
            if (s[4] == search) return true
        } catch (e: IndexOutOfBoundsException) {
            // this should never happen...
            return false
        }
        return false
    }

    fun notifySelection(orderList: List<OrderItem>, status: String?) {
        val tempList: MutableList<OrderItem> = ArrayList()
        for (x in orderList.indices) {
            if (orderList[x].status.equals(status, ignoreCase = true)) {
                tempList.add(orderList[x])
            }
            this.orderList = tempList
        }
        notifyDataSetChanged()
    }
    inner class OrderHolder(var binding: OrderDetailsItemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.M)
        fun setDataBind(orders: OrderItem, position: Int) {
            binding.orderName.text = TextUtils.getString(orders.orderNo.toString())
            binding.date.text = TextUtils.getString(orders.createdAt).substringBefore("T")

            if (orders.customer != null) {
                binding.customer.text = TextUtils.getString(orders.customer.name)
            }
            if (TextUtils.getString(orders.status) == "completed") {
                binding.Fulfillment.text = "Competed"
                binding.Fulfillment.setBackgroundResource(R.drawable.green_10_bg)
            } else if (TextUtils.getString(orders.status) == "pending") {
                binding.Fulfillment.text = "Awaiting processing"
                binding.Fulfillment.setBackgroundResource(R.drawable.yellow_10_bg)

            }  else if (TextUtils.getString(orders.status) == "archived") {
                binding.Fulfillment.text = "Archived"
                binding.Fulfillment.setBackgroundResource(R.drawable.yellow_10_bg)

            } else if (TextUtils.getString(orders.status) == "canceled") {
                binding.Fulfillment.text = "Canceled"
                binding.Fulfillment.setBackgroundResource(R.drawable.red_10_bg)
            }else if (TextUtils.getString(orders.status) == "ready-for-pickup") {
                binding.Fulfillment.text = "Ready for pickup"
                binding.Fulfillment.setBackgroundResource(R.drawable.sky_10_bg)
            }else if (TextUtils.getString(orders.status) == "processing") {
                binding.Fulfillment.text = "Processing"
                binding.Fulfillment.setBackgroundResource(R.drawable.blue_10_bg)
            }

//            } else if (TextUtils.getString(orders.getStatus()).equals("3")) {
//                binding.Fulfillment.setText("Awaiting processing");
//                binding.Fulfillment.setBackgroundResource(R.drawable.red_10bg);
//            }
            binding.orderTotal.text = TextUtils.getString(orders.total.toString())
            binding.items.text = TextUtils.getString(orders.orderItemsCount.toString())
            if (TextUtils.getString(orders.paymentStatus).equals("1", ignoreCase = true)) {
                binding.payment.setBackgroundResource(R.drawable.green_10_bg)
                binding.payment.text = "Completed"
            } else if (TextUtils.getString(orders.paymentStatus).equals("2", ignoreCase = true)) {
                binding.payment.setBackgroundResource(R.drawable.yellow_10_bg)
                //  binding.payment.setPadding(0, 0, 0, 0);
                binding.payment.text = "Pending"
            }
            if (currentPos == position) {
                binding.orderRbtn.isChecked = true
            } else {
                binding.orderRbtn.isChecked = false
            }
            binding.orderRbtn.setOnClickListener { view: View? ->
                currentPos = position
                notifyDataSetChanged()
            }


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