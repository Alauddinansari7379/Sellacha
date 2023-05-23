package com.android.sellacha.Shipping.ShippingPrice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.bumptech.glide.Glide


class AdapterShipping(val context: Context, private val list: ModelCategory,) :
    RecyclerView.Adapter<AdapterShipping.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.shipping_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.nameShipping.text = list.data.posts.data[position].name
        holder.priceShipping.text = list.data.posts.data[position].slug

      //   Glide.with(holder.categoryImg).load(list.data.posts.data[position].preview).into(holder.categoryImg)

    }


    override fun getItemCount(): Int {
        return list.data.posts.data.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val nameShipping: TextView = itemView.findViewById(R.id.nameShipping)
          val priceShipping: TextView = itemView.findViewById(R.id.priceShipping)


    }
}