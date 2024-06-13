package com.android.sellacha.Products.categories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.R
import com.squareup.picasso.Picasso


class AdapterCategory(
    val context: Context, private val list: ArrayList<com.android.sellacha.Products.categories.ModelGetCategory.DataX>,
    val delete: Delete) :
    RecyclerView.Adapter<AdapterCategory.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.categories_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"
        holder.nameTvCat.text = list[position].name
        if (list[position].preview != null) {
            Picasso.get().load("https://sellacha.com/"+list[position].preview!!.content)
                .placeholder(R.drawable.placeholder_n).error(R.drawable.error_placeholder)
                .into(holder.categoryImg)


        }

        holder.imgDeleteC.setOnClickListener {
            delete.delete(list[position].id.toString())
        }

        holder.imgEditCat.setOnClickListener {
            delete.edit(list[position].id.toString(),list[position].name)
        }

    }


    override fun getItemCount(): Int {
        return list.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val nameTvCat: TextView = itemView.findViewById(R.id.nameTvCat)
          val categoryImg: ImageView = itemView.findViewById(R.id.categoryImg)
          val imgDeleteC: ImageView = itemView.findViewById(R.id.imgDeleteCat)
          val imgEditCat: ImageView = itemView.findViewById(R.id.imgEditCat)
    }
    interface Delete {
        fun delete(id: String)
        fun edit(id: String,name: String,)
    }
}