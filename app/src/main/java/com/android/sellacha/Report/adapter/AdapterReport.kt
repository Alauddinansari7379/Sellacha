package com.android.sellacha.Transaction.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.Report.model.ModelReort
import com.android.sellacha.Transaction.Model.ModelTransaction
import com.bumptech.glide.Glide


class AdapterReport(val context: Context, private val list: ModelReort) :
    RecyclerView.Adapter<AdapterReport.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.report_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.amt.text = list.data.orders.data[position].order_no
        holder.paymentStatus.text = list.data.orders.data[position].status
        holder.payMethod.text = "₹"+list.data.orders.data[position].total
//        holder.orderNum.text = list.data.orders.data[position].customer.name
//        holder.tansNum.text = list.data.orders.data[position].customer.email


        when (list.data.orders.data[position].status) {
            "completed" -> {
                holder.paymentStatus.setBackgroundResource(R.drawable.bg_green)
                holder.paymentStatus.text = "Completed"
            }

            "ready-for-pickup" -> {
                holder.paymentStatus.setBackgroundResource(R.drawable.sky_10_bg)
                holder.paymentStatus.text = "Ready for pickup"

            }
            "awaiting processing" -> {
                holder.paymentStatus.setBackgroundResource(R.drawable.bg_yellow)
                holder.paymentStatus.text = "Awaiting Processing"

            }
            "archived" -> {
                holder.paymentStatus.setBackgroundResource(R.drawable.bg_yellow)
                holder.paymentStatus.text = "Archived"

            }
            "pending" -> {
                holder.paymentStatus.setBackgroundResource(R.drawable.bg_yellow)
                 holder.paymentStatus.text = "Pending"

            }
            "canceled" -> {
                holder.paymentStatus.setBackgroundResource(R.drawable.red_10_bg)
                holder.paymentStatus.text = "Canceled"

            }
            "processing" -> {
                holder.paymentStatus.setBackgroundResource(R.drawable.blue_10_bg)
                holder.paymentStatus.text = "Processing"

            }
            else -> {

            }
        }
    }


    override fun getItemCount(): Int {
        return list.data.orders.data.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amt: TextView = itemView.findViewById(R.id.amtreport)
        val paymentStatus: TextView = itemView.findViewById(R.id.paymentStatusreport)
        val payMethod: TextView = itemView.findViewById(R.id.payMethodreport)


    }
}