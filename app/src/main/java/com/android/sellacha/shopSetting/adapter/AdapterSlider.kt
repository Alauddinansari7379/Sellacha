package com.android.sellacha.shopSetting.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.OfferAndAds.model.ModelBumpAd
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.shopSetting.model.ModelGetSlider
import com.android.sellacha.utils.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso


class AdapterSlider(val context: Context, private val list: ModelGetSlider, val delete: Delete) :
    RecyclerView.Adapter<AdapterSlider.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.bump_ad_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"
        holder.urlBump.text = list.data.posts[position].slug
        if (list.data.posts[position].name != null) {
             Picasso.get().load("https://storefront.sellacha.com/"+list.data.posts[position].name)
                .placeholder(R.drawable.placeholder_n)
                .error(R.drawable.error_placeholder)
                .into(holder.img)

        }
        holder.imgDeleteBum.setOnClickListener {
             delete.delete(list.data.posts[position].id.toString())
        }




    }


    override fun getItemCount(): Int {
        return list.data.posts.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val urlBump: TextView = itemView.findViewById(R.id.urlBump)
          val img: ImageView = itemView.findViewById(R.id.bumpImg)
          val imgDeleteBum: ImageView = itemView.findViewById(R.id.imgDeleteBum)


    }
    interface Delete{
        fun delete(id:String)

    }
}