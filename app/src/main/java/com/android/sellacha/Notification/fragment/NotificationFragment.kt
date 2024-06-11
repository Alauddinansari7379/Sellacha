package com.android.sellacha.Notification.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sellacha.Notification.Adapter.NotificationAdapter
import com.android.sellacha.Notification.Model.ModelNotification
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentNotificationBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.sharedpreferences.SessionManager
import com.android.sellacha.utils.AppProgressBar
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : Fragment() {
    var notificationResponses: ArrayList<ModelNotification> = ArrayList()
    var binding: FragmentNotificationBinding? = null
    var notificationAdapter: NotificationAdapter? = null
    lateinit var sessionManager: SessionManager
    var count = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)

//        notificationResponses.add(NotificationResponse("Your Order Delivered", "15 Jan 2022"))
//        notificationResponses.add(NotificationResponse("Your Order Delivered", "12 Feb 2022"))
//        notificationResponses.add(NotificationResponse("Your Order Delivered", "22 May 2022"))
//        notificationResponses.add(NotificationResponse("Your Order Delivered", "15 Jun 2022"))
//        notificationResponses.add(NotificationResponse("Your Order Delivered", "16 Jul 2022"))
//        notificationResponses.add(NotificationResponse("Your Order Delivered", "13 Jul 2022"))
//        notificationResponses.add(NotificationResponse("Your Order Delivered", "01 Jul 2022"))

        sessionManager = SessionManager(activity)
        apiCalLoginNimbus()

        return binding!!.getRoot()
    }

    private fun apiCalLoginNimbus() {
        AppProgressBar.showLoaderDialog(activity)


        ApiClient.apiService.notiList(
            sessionManager.authToken.toString(),
        )
            .enqueue(object : Callback<ModelNotification> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelNotification>, response: Response<ModelNotification>
                ) {
                    try {
                        if (response.code() == 500) {
                            activity?.let { myToast(it, "Server Error") }
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            activity?.let { myToast(it, "Something went wrong") }
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            if (response.body()!!.data.isEmpty()){
                                activity?.let { myToast(it, "Notification not found") }
                                AppProgressBar.hideLoaderDialog()

                            }else{
                                binding!!.notificationList.layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                notificationAdapter = NotificationAdapter(context!!, response.body()!!.data)
                                binding!!.notificationList.adapter = notificationAdapter
                                AppProgressBar.hideLoaderDialog()

                            }

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        activity?.let { myToast(it, "Something went wrong") }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelNotification>, t: Throwable) {
                    count++
                    if (count <= 3) {
                        Log.e("count", count.toString())
                        apiCalLoginNimbus()
                    } else {
                        activity?.let { myToast(it, t.message.toString()) }
                        AppProgressBar.hideLoaderDialog()

                    }
                    AppProgressBar.hideLoaderDialog()
                }


            })
    }

}