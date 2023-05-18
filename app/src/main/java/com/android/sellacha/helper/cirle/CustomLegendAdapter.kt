package com.faskn.clickablepiechart

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.android.sellacha.R
import com.faskn.lib.Slice
import com.faskn.lib.legend.LegendAdapter
import com.faskn.lib.legend.LegendItemViewHolder


class CustomLegendAdapter: LegendAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomLegendItemViewHolder {
        return CustomLegendItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_item_legend, parent, false)
        )
    }

    // CREATE YOUR OWN ITEM VIEW HOLDER
    class CustomLegendItemViewHolder(view: View) : LegendItemViewHolder(view) {
       override fun bind(slice: Slice) {
           var image :ImageView
           image = itemView.findViewById(R.id.imageViewCircleIndicator)
           this.boundItem = slice
           image.imageTintList =
               ColorStateList.valueOf(ContextCompat.getColor(itemView.context, slice.color))
          // itemView.textViewSliceTitle.text = slice.name
       }
   }
}