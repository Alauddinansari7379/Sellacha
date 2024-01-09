package com.android.sellacha.Shipping.Location.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.bumptech.glide.Glide


class AdapterLoaction(val context: Context,  val list: ArrayList<DataX>,val delete: Delete) :
    RecyclerView.Adapter<AdapterLoaction.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.location_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"

        holder.nameTvCat.text = list[position].name

        holder.imgDeleteLocation.setOnClickListener {
            delete.delete(list[position].id.toString())
        }

        holder.imgEditLocation.setOnClickListener {
            delete.edit(list[position].id.toString(),list[position].name)
        }



      //   Glide.with(holder.categoryImg).load(list.data.posts.data[position].preview).into(holder.categoryImg)

    }


    override fun getItemCount(): Int {
        return list.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val nameTvCat: TextView = itemView.findViewById(R.id.LocationName)
          val imgDeleteLocation: ImageView = itemView.findViewById(R.id.imgDeleteLocation)
          val imgEditLocation: ImageView = itemView.findViewById(R.id.imgEditLocation)


    }

    interface Delete{
        fun delete(id:String)
        fun edit(id:String,title:String)
    }
}