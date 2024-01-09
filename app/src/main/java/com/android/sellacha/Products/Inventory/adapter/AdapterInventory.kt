package com.android.sellacha.Products.Inventory.adapter

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.Products.Inventory.Model.DataInverntor
import com.android.sellacha.Products.Inventory.Model.Modelinventory
import com.android.sellacha.Products.Inventory.activity.InventoryFragment
 import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.Registration.StoreInformation
import com.squareup.picasso.Picasso


class AdapterInventory(val context: Context, private val list: ArrayList<DataInverntor>,val updateInventory: UpdateInventory) :
    RecyclerView.Adapter<AdapterInventory.MyViewHolder>() {
    var statuseList = java.util.ArrayList<ModelProductType>()
    var st=false
    var statuse=""




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.SrNo.text= "${position+1}"
        statuseList.add(ModelProductType("In stock", 1))
        statuseList.add(ModelProductType("Out Of stock", 0))

        try {
            holder!!.status.adapter = ArrayAdapter<ModelProductType>(
                context,
                R.layout.simple_list_item_1,
                statuseList
            )
            holder!!.status.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        adapterView: AdapterView<*>?,
                        view: View?,
                        i: Int,
                        l: Long
                    ) {
                        if (statuseList.size > 0) {
                             statuse = statuseList[i].value.toString()
//                            if (st){
//                                holder.tvinStockNew.text=statuseList[i].text
//                                holder.tvinStockNew.visibility=View.VISIBLE
//                                holder.tvinStock.visibility=View.GONE
//                            }
//                            st=true


                            Log.e(ContentValues.TAG, "statuse: $statuse")
                        }
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                }

            if (list[position].sku == "null") {

            } else {
                holder.skuTv.text = list[position].sku

            }
            holder.categoryTv.text = list[position].term.title
            holder.edtQty.setText(list[position].stock_qty)


            if (list[position].stock_status=="1"){
                holder.tvinStock.text ="In stock"
            }
            if (list[position].stock_status=="0"){
                holder.tvinStock.text ="Out Of stock"
               // holder.edtQty.visibility=View.GONE
            }

            if (list[position].stock_manage=="0"){
                 holder.edtQty.visibility=View.GONE
            }

            // holder.varitionsTv.text = list.data.posts[position].v
            when (list[position].stock_manage) {
                "0" -> {
                    holder.stockManageTv.text = "No"
                    holder.stockManageTv.setBackgroundResource(R.drawable.bg_red)

                }
                else -> {
                    holder.stockManageTv.text = "Yes"
                    holder.stockManageTv.setBackgroundResource(R.drawable.bg_green)

                }
            }

            if (list[position].term.preview != null) {
                Picasso.get()!!.load("${"https:" + list[position].term.preview.media.url}")
                    .placeholder(R.drawable.placeholder_n)
                    .error(R.drawable.error_placeholder)
                    .into(holder.categoryImg)
                Log.e("code", "${"https:" + list[position].term.preview.media.url}")

            }
            holder.btnSave.setOnClickListener {
                updateInventory.updateInventory(list[position].id.toString(),statuse,holder.edtQty.text.toString())
            }






            // Glide.with(holder.categoryImg).load(list.data.posts.data[position].preview.content).into(holder.categoryImg)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list.size


    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skuTv: TextView = itemView.findViewById(R.id.skuTv)
        val categoryTv: TextView = itemView.findViewById(R.id.categoryTv)
        val stockManageTv: TextView = itemView.findViewById(R.id.stockManageTv)
        val categoryImg: ImageView = itemView.findViewById(R.id.categoryImg)
        val status: Spinner = itemView.findViewById(R.id.status)
        val tvinStock: TextView = itemView.findViewById(R.id.tvinStock)
        val tvinStockNew: TextView = itemView.findViewById(R.id.tvinStockNew)
        val edtQty: EditText = itemView.findViewById(R.id.edtQty)
        val btnSave: Button = itemView.findViewById(R.id.btnSave)


    }
    interface UpdateInventory{
        fun updateInventory(id:String,status:String,qty:String)
    }
}