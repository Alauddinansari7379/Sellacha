package com.android.sellacha.Products.Attributes.adapter

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


class AdapterAttribute(val context: Context, private val list: ArrayList<Post>,val delete: Delete) :
    RecyclerView.Adapter<AdapterAttribute.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.attributes_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.name.text = list[position].name
       // holder.varitionsTv.text = list.data.posts[position].v

        holder.imgDeleteAtt.setOnClickListener {
            delete.delete(list[position].id.toString())
        }

        holder.imgEditAtt.setOnClickListener {
            delete.edit(list[position].name,list[position].id.toString())
        }


    }


    override fun getItemCount(): Int {
        return list.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val name: TextView = itemView.findViewById(R.id.nameAtt)
          val varitionsTv: TextView = itemView.findViewById(R.id.VARIATIONSAtt)
          val imgDeleteAtt: ImageView = itemView.findViewById(R.id.imgDeleteAtt)
          val imgEditAtt: ImageView = itemView.findViewById(R.id.imgEditAtt)


    }
    interface Delete {
        fun delete(id: String)
        fun edit(id: String,name: String,)
    }
    }