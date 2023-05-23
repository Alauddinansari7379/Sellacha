package com.android.sellacha.Products.Brands.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.bumptech.glide.Glide
import com.example.ehcf.sharedpreferences.SessionManager
import com.squareup.picasso.Picasso


class AdapterBrand(val context: Context, private val list: ModelCategory) :
    RecyclerView.Adapter<AdapterBrand.MyViewHolder>() {

    lateinit var sessionManager: SessionManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.brans_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"
        sessionManager = SessionManager(context)
        holder.nameBrand.text = list.data.posts.data[position].name
        Picasso.get().load("https://footwear.thedemostore.in/"+list.data.posts.data[position].preview!!.content).into(holder.categoryImg)

      //  Picasso.with(this).load("https://footwear.thedemostore.in/uploads/21/22/12/1672393071.png").into(holder.categoryImg);
      //  Picasso.get().load("https://footwear.thedemostore.in/uploads/21/22/12/1672393071.png").into(holder.categoryImg)
       // Glide.with(holder.categoryImg).load("https://footwear.thedemostore.in/uploads/21/22/12/1672393071.png").into(holder.categoryImg)


    }


    override fun getItemCount(): Int {
        return list.data.posts.data.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameBrand: TextView = itemView.findViewById(R.id.nameTvBrand)
        val categoryImg: ImageView = itemView.findViewById(R.id.categoryImg)


    }
}