package com.android.sellacha.Transaction.adapter

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
import com.android.sellacha.Transaction.Model.ModelTransaction
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso


class AdapterTransaction(val context: Context, private val list: ModelTransaction,) :
    RecyclerView.Adapter<AdapterTransaction.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.transation_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.amt.text = list.data.orders.data[position].total
        holder.paymentStatus.text = list.data.orders.data[position].status
        if (list.data.orders.data[position].getway != null) {
            holder.payMethod.text = list.data.orders.data[position].getway.name

        }
        holder.orderNum.text = list.data.orders.data[position].order_no
        holder.tansNum.text = list.data.orders.data[position].transaction_id


        when(list.data.orders.data[position].status) {
            "completed" -> {
                holder.paymentStatus.setBackgroundResource(R.drawable.bg_green);

            }
            "pending" -> {
                holder.paymentStatus.setBackgroundResource(R.drawable.bg_yellow);

            }
            else -> {

            }
        }
    }


    override fun getItemCount(): Int {
        return list.data.orders.data.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val amt: TextView = itemView.findViewById(R.id.amt)
          val paymentStatus: TextView = itemView.findViewById(R.id.paymentStatus)
          val payMethod: TextView = itemView.findViewById(R.id.payMethod)
          val orderNum: TextView = itemView.findViewById(R.id.orderNum)
          val tansNum: TextView = itemView.findViewById(R.id.tansNum)


    }
}