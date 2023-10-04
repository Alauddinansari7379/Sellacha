package com.android.sellacha.createOrder.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Order.Model.Item
import com.android.sellacha.R
import com.squareup.picasso.Picasso


class AdapterCart(
    val context: Context,
    private val list: ArrayList<Item>,
    val removeCart: RemoveCart
) :
    RecyclerView.Adapter<AdapterCart.MyViewHolder>() {
    private var subtotal = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.itemName.text = list[position].term_title
        holder.itemQty.text = list[position].qty
        holder.itemPrice.text = "₹" + list[position].price
        holder.subtotal.text = "₹" + list[position].subtotal.toInt()
         removeCart.totalAmount(list[position].final_total)

        Picasso.get()!!.load("${"http:" + list[position].preview}")
            .placeholder(R.drawable.placeholder_n)
            .error(R.drawable.error_placeholder)
            .into(holder.imgItem)

        holder.removeCart.setOnClickListener {
            removeCart.removeCart(list[position].id.toString())
         }

    }


    override fun getItemCount(): Int {
        return list.size

    }



    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvItemName)
        val itemQty: TextView = itemView.findViewById(R.id.tvQty)
        val itemPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val subtotal: TextView = itemView.findViewById(R.id.tvSubtotal)
        val imgItem: ImageView = itemView.findViewById(R.id.imgItem)
        val removeCart: ImageView = itemView.findViewById(R.id.removeCart)


    }

    interface RemoveCart {
        fun removeCart(id: String)
        fun totalAmount(finerTotal: String)
    }
}

