package com.android.sellacha.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.Attributes.activity.MOdel.Data
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.Attributes.activity.MOdel.Post
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.setting.model.ModelSubscription
import com.android.sellacha.utils.TextUtils


class AdapterSubscription(val context: Context, private val list: ArrayList<com.android.sellacha.setting.model.Data>) :
    RecyclerView.Adapter<AdapterSubscription.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.single_row_subscerption, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.orderNameS.text = list[position].order_no
        holder.nameS.text = list[position].plan.name
        holder.purchase.text = list[position].created_at.substringBeforeLast("T")
        holder.expiry.text = list[position].will_expire
        holder.totalSub.text = list[position].amount.toString()
        holder.paymentMethod.text = list[position].category.name
        holder.tax.text = list[position].tax.toString()

         if (TextUtils.getString(list[position].payment_status.toString()) == "1") {
             holder.paymentStatues.text= "Paid"
             holder.paymentStatues.setBackgroundResource(R.drawable.green_10_bg)
        }

        if (TextUtils.getString(list[position].status.toString()) == "1") {
             holder.fullFillMent.text= "Approved"
             holder.fullFillMent.setBackgroundResource(R.drawable.green_10_bg)
        }
//        holder.paymentStatues.text = list[position].payment_status.toString()
//        holder.fullFillMent.text = list[position].status.toString()




    }


    override fun getItemCount(): Int {
        return list.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val orderNameS: TextView = itemView.findViewById(R.id.orderNameS)
          val nameS: TextView = itemView.findViewById(R.id.nameS)
          val purchase: TextView = itemView.findViewById(R.id.PURCHASE)
          val expiry: TextView = itemView.findViewById(R.id.EXPIRY)
          val totalSub: TextView = itemView.findViewById(R.id.totalSub)
          val tax: TextView = itemView.findViewById(R.id.TAX)
          val paymentMethod: TextView = itemView.findViewById(R.id.PAYMENTM)
          val paymentStatues: TextView = itemView.findViewById(R.id.PAYMENTS)
          val fullFillMent: TextView = itemView.findViewById(R.id.FULFILLMENT)


    }

    }