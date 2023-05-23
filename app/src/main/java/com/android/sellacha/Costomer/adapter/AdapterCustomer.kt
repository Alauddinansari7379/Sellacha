package com.android.sellacha.Costomer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R


class AdapterCustomer(val context: Context, private val list: ModelCategory,) :
    RecyclerView.Adapter<AdapterCustomer.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.customer_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.name.text = list.data.posts.data[position].name
        holder.email.text = list.data.posts.data[position].email
       // holder.varitionsTv.text = list.data.posts[position].v

        // Glide.with(holder.categoryImg).load(list.data.posts.data[position].preview.content).into(holder.categoryImg)

    }


    override fun getItemCount(): Int {
        return list.data.posts.data.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val name: TextView = itemView.findViewById(R.id.name)
          val email: TextView = itemView.findViewById(R.id.email)


    }
}