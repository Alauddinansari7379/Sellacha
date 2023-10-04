package com.android.sellacha.Shipping.ShippingPrice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.bumptech.glide.Glide


class AdapterShipping(val context: Context, private val list: ArrayList<DataX>,val delete: Delete) :
    RecyclerView.Adapter<AdapterShipping.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.shipping_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.nameShipping.text = list[position].name
        holder.priceShipping.text = list[position].slug
        holder.imgDeleteShi.setOnClickListener {
            delete.delete(list[position].id.toString())
        }

        holder.imgEditShi.setOnClickListener {
            delete.edit(list[position].id.toString(),list[position].name,list[position].slug)
        }


    }


    override fun getItemCount(): Int {
        return list.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val nameShipping: TextView = itemView.findViewById(R.id.nameShipping)
          val priceShipping: TextView = itemView.findViewById(R.id.priceShipping)
          val imgDeleteShi: ImageView = itemView.findViewById(R.id.imgDeleteShi)
          val imgEditShi: ImageView = itemView.findViewById(R.id.imgEditShi)


    }

    interface Delete {
        fun delete(id: String)
        fun edit(id: String,title: String,price: String,)
    }
}