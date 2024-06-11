package com.android.sellacha.Notification.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.R
import com.android.sellacha.databinding.NotificationItemBinding

class NotificationAdapter(var context: Context, var notificationList: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private fun getViewHolder(inflater: LayoutInflater, group: ViewGroup): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<NotificationItemBinding>(
            inflater,
            R.layout.notification_item,
            group,
            false
        )
        return NotificationHolder(binding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(context), parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val vHolder = holder as NotificationHolder
//        vHolder.binding.notificationTxt.text = notificationList[position].data.toString()
//        vHolder.binding.notificationTime.text = notificationList[position].date
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class NotificationHolder(var binding: NotificationItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}