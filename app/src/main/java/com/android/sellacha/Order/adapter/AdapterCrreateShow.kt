package com.android.sellacha.Order.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Order.Model.ModelCreateShow
import com.android.sellacha.Order.activity.CreateOrderShowFragment
import com.android.sellacha.Products.Inventory.Model.Modelinventory
import com.android.sellacha.R
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso


class AdapterCrreateShow(val context: Context, private val list: ModelCreateShow,val cart:AddProduct) :
    RecyclerView.Adapter<AdapterCrreateShow.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.create_show_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        if (list.data.posts.data[position].preview != null) {
            //Glide.with(holder.imgShow).load("https:" + list.data.posts.data[position].preview.media.url).into(holder.imgShow)
            Picasso.get().load("https:"+list.data.posts.data[position].preview.media.url).into(holder.imgShow)
            Log.e("Tag","https:" + list.data.posts.data[position].preview.media.url)

        }
        holder.nameShow.text = list.data.posts.data[position].title
        if (list.data.posts.data[position].price != null) {
            holder.priceShow.text = list.data.posts.data[position].price.price


        }

        }
        // Glide.with(holder.categoryImg).load(list.data.posts.data[position].preview.content).into(holder.categoryImg)




    override fun getItemCount(): Int {
        return list.data.posts.data.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgShow: ImageView = itemView.findViewById(R.id.ImgShow)
        val nameShow: TextView = itemView.findViewById(R.id.nameShow)
        val priceShow: TextView = itemView.findViewById(R.id.priceShow)


    }
    interface AddProduct{
        fun addItem()
    }
}