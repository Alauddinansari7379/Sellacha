package com.android.sellacha.Products.Coupons.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.Coupons.MOdel.DataX
import com.android.sellacha.R


class AdapterCoupons(val context: Context, private val list: ArrayList<DataX>, val delete: Delete) :
    RecyclerView.Adapter<AdapterCoupons.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.coupon_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.name.text = list[position].name
        holder.dateCoupon.text = list[position].slug

        holder.imgDeleteC.setOnClickListener {
            delete.delete(list[position].id.toString())
        }
        holder.imgEditCoupon.setOnClickListener {
            delete.edit(list[position].name,list[position].slug,list[position].id.toString())
        }


    }


    override fun getItemCount(): Int {
        return list.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameCoupon)
        val dateCoupon: TextView = itemView.findViewById(R.id.dateCoupon)
        val imgDeleteC: ImageView = itemView.findViewById(R.id.imgDeleteC)
        val imgEditCoupon: ImageView = itemView.findViewById(R.id.imgEditCoupon)


    }

    interface Delete {
        fun delete(id: String)
        fun edit( code: String, exDate: String,id: String,)
    }
}