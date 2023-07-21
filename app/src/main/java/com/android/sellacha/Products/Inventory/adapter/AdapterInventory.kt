package com.android.sellacha.Products.Inventory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.Inventory.Model.Modelinventory
import com.android.sellacha.R


class AdapterInventory(val context: Context, private val list: Modelinventory) :
    RecyclerView.Adapter<AdapterInventory.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        if (list.data.posts.data[position].sku == "null"){

        }else{
            holder.skuTv.text = list.data.posts.data[position].sku

        }
        holder.categoryTv.text = list.data.posts.data[position].term.title
        // holder.varitionsTv.text = list.data.posts[position].v
        when (list.data.posts.data[position].stock_manage) {
            "0" -> {
                holder.stockManageTv.text = "No"
                holder.stockManageTv.setBackgroundResource(R.drawable.bg_red);

            }
            else -> {
                holder.stockManageTv.text = "Yes"
                holder.stockManageTv.setBackgroundResource(R.drawable.bg_green);

            }

        }
        // Glide.with(holder.categoryImg).load(list.data.posts.data[position].preview.content).into(holder.categoryImg)

    }


    override fun getItemCount(): Int {
        return list.data.posts.data.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skuTv: TextView = itemView.findViewById(R.id.skuTv)
        val categoryTv: TextView = itemView.findViewById(R.id.categoryTv)
        val stockManageTv: TextView = itemView.findViewById(R.id.stockManageTv)


    }
}