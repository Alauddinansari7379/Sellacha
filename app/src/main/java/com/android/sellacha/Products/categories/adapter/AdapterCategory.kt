package com.android.sellacha.Products.categories.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.categories.Model.ModeCategoryJava
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso


class AdapterCategory(val context: Context, private val list: ModelCategory,) :
    RecyclerView.Adapter<AdapterCategory.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.categories_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"
        holder.nameTvCat.text = list.data.posts.data[position].name
        Picasso.get().load("https://footwear.thedemostore.in/"+list.data.posts.data[position].preview!!.content).into(holder.categoryImg)

        // holder.nameTvCat.text = list.data.posts.data[position].preview.content
      //  val baseUrl=list.data.posts.data[position].preview.content
       // Log.e("BAse",baseUrl)

       // Glide.with(holder.categoryImg).load(baseUrl).into(holder.categoryImg)

    }


    override fun getItemCount(): Int {
        return list.data.posts.data.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val nameTvCat: TextView = itemView.findViewById(R.id.nameTvCat)
          val categoryImg: ImageView = itemView.findViewById(R.id.categoryImg)


    }
}