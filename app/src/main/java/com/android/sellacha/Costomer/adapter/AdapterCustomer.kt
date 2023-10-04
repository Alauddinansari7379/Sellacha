package com.android.sellacha.Costomer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R


class AdapterCustomer(val context: Context, private val list: ArrayList<DataX>,val delete: Delete,) :
    RecyclerView.Adapter<AdapterCustomer.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.customer_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.name.text = list[position].name
        holder.email.text = list[position].email
       // holder.varitionsTv.text = list.data.posts[position].v

        holder.imgDeleteCus.setOnClickListener {
            delete.delete(list[position].id.toString())
        }

        holder.imgEditCus.setOnClickListener {
            delete.edit(list[position].id.toString(),list[position].name,list[position].email)
        }

    }


    override fun getItemCount(): Int {
        return list.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val name: TextView = itemView.findViewById(R.id.name)
          val email: TextView = itemView.findViewById(R.id.email)
          val imgDeleteCus: ImageView = itemView.findViewById(R.id.imgDeleteCus)
          val imgEditCus: ImageView = itemView.findViewById(R.id.imgEditCus)


    }
    interface Delete {
        fun delete(id: String)
        fun edit(id: String,name: String,email: String,)
    }
}