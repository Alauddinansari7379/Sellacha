package com.android.sellacha.ReviewAndRating.adapter

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


class AdapterReviewAndRating(val context: Context, private val list: ModelCategory,) :
    RecyclerView.Adapter<AdapterReviewAndRating.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.review_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"
        holder.ratingsTv.text = list.data.posts.data[position].rating
        holder.ratingNameTv.text = list.data.posts.data[position].name


    }


    override fun getItemCount(): Int {
        return list.data.posts.data.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val ratingsTv: TextView = itemView.findViewById(R.id.ratingsTv)
          val ratingNameTv: TextView = itemView.findViewById(R.id.ratingNameTv)


    }
}