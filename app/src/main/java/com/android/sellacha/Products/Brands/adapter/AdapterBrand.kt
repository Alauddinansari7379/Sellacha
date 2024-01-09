package com.android.sellacha.Products.Brands.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.R
import com.android.sellacha.sharedpreferences.SessionManager
import com.squareup.picasso.Picasso


class AdapterBrand(val context: Context, private val list: ArrayList<DataX>,val delete: Delete) :
    RecyclerView.Adapter<AdapterBrand.MyViewHolder>() {

    lateinit var sessionManager: SessionManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.brans_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"
        try {
            sessionManager = SessionManager(context)
            holder.nameBrand.text = list[position].name
            if ( list[position].preview!!.content!=null) {
                Picasso.get().load("https://sellacha.com/" + list[position].preview!!.content)
                    .placeholder(R.drawable.placeholder_n)
                    .error(R.drawable.error_placeholder)
                    .into(holder.categoryImg)
            }

                holder.imgDeleteBrand.setOnClickListener {
                    delete.delete(list[position].id.toString())
                }
                holder.imgEditBrand.setOnClickListener {
                    delete.edit(list[position].id.toString(), list[position].name)
                }
        }catch (e:Exception){
            
        }
    }


    override fun getItemCount(): Int {
        return list.size

    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameBrand: TextView = itemView.findViewById(R.id.nameTvBrand)
        val categoryImg: ImageView = itemView.findViewById(R.id.categoryImg)
        val imgDeleteBrand: ImageView = itemView.findViewById(R.id.imgDeleteBrand)
        val imgEditBrand: ImageView = itemView.findViewById(R.id.imgEditBrand)


    }
    interface Delete {
        fun delete(id: String)
        fun edit(id: String,name: String,)
    }
}