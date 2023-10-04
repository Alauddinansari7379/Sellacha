package com.android.sellacha.Order.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sellacha.Order.Model.ModelCoupon
import com.android.sellacha.R
import com.android.sellacha.adapter.OrderInfoAdapter
import com.android.sellacha.api.ApiResponse
import com.android.sellacha.api.response.OrderByID.OrderByID
import com.android.sellacha.api.response.OrderByID.OrderItemItem
import com.android.sellacha.api.service.MainService
import com.android.sellacha.databinding.FragmentOrderDetailBinding
import com.android.sellacha.dialog.AppDialog
import com.android.sellacha.fragment.BaseFragment
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.TextUtils
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import com.google.gson.Gson
import com.google.gson.JsonNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailFragment : BaseFragment() {
    lateinit var binding: FragmentOrderDetailBinding
    var orderAdapter: OrderInfoAdapter? = null
    var orderID = 0
    lateinit var sessionManager: SessionManager
    var orderList: List<OrderItemItem> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)
        if (arguments != null) {
            orderID = arguments!!.getInt("orderID")
        }
        orderByID
        binding.printInvoice.setOnClickListener { view: View? ->
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://thedemostore.in/seller/orders/invoicenew/21/22")
            )
            startActivity(browserIntent)
        }
        binding.paymentOption.setOnClickListener { v: View? -> paymentDialog(binding.paymentOption) }
        binding.orderStatusSelector.setOnClickListener { v: View? -> filterDialog(binding.orderStatusSelector) }
        sessionManager = SessionManager(requireContext())

        binding.btn1.setOnClickListener {

            if (binding.btn1.text.toString()=="Save Payment Status"){
                apiCallPaymentChange()
            }
            if (binding.btn1.text.toString()=="Save Order Status"){
                apiCallOrderChange()
            }

        }
        return binding.getRoot()
    }

    private val orderByID: Unit
        get() {
            AppProgressBar.showLoaderDialog(mContext)
            MainService.getOrderByID(mContext, orderID)
                .observe(viewLifecycleOwner) { response: ApiResponse? ->
                    if (response == null) {
                        errorSnackBar(binding!!.root, getString(R.string.something_wrong))
                    } else {
                        if (response.data !is JsonNull) {
                            if (response.data != null) {
                                val orderByID =
                                    Gson().fromJson(response.data, OrderByID::class.java)
                                setData(orderByID)
                            } else {
                                showAlertDialog(
                                    getString(R.string.app_name),
                                    response.message,
                                    "OK",
                                    ""
                                ) { obj: AppDialog -> obj.dismiss() }
                            }
                        } else {
                            errorSnackBar(binding!!.root, response.message)
                        }
                    }
                    AppProgressBar.hideLoaderDialog()
                }
        }

    private fun apiCallOrderChange() {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.orderChange(
            sessionManager.authToken,
            orderId,
            statuesOrder,
        )
            .enqueue(object : Callback<ModelCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupon>, response: Response<ModelCoupon>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.code() == 401) {
                        myToast(requireActivity(), "Something went wrong")
                        // myToast(requireActivity(), "Maximum Attribute limit exceeded")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                         myToast(requireActivity(), response.body()!!.data)

                        if (TextUtils.getString(statuesOrder) == "completed") {
                            binding!!.orderStatusC.text = "Competed"
                            binding!!.orderStatusC.setBackgroundResource(R.drawable.green_10_bg)
                        } else if (TextUtils.getString(statuesOrder) == "pending") {
                            binding!!.orderStatusC.text = "Awaiting processing"
                            binding!!.orderStatusC.setBackgroundResource(R.drawable.yellow_10_bg)
                        } else if (TextUtils.getString(statuesOrder) == "archived") {
                            binding!!.orderStatusC.text = "Archived"
                            binding!!.orderStatusC.setBackgroundResource(R.drawable.yellow_10_bg)
                        } else if (TextUtils.getString(statuesOrder) == "canceled") {
                            binding!!.orderStatusC.text = "Canceled"
                            binding!!.orderStatusC.setBackgroundResource(R.drawable.red_10_bg)
                        } else if (TextUtils.getString(statuesOrder) == "ready-for-pickup") {
                            binding.orderStatusC.text = "Ready for pickup"
                            binding.orderStatusC.setBackgroundResource(R.drawable.sky_10_bg)
                        } else if (TextUtils.getString(statuesOrder) == "processing") {
                            binding.orderStatusC.text = "Processing"
                            binding.orderStatusC.setBackgroundResource(R.drawable.blue_10_bg)
                        }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                    apiCallOrderChange()


                }

            })
    }
    private fun apiCallPaymentChange() {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.paymentChange(
            sessionManager.authToken,
            orderId,
            statuesPayment,
        )
            .enqueue(object : Callback<ModelCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupon>, response: Response<ModelCoupon>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.code() == 401) {
                        myToast(requireActivity(), "Something went wrong")
                        // myToast(requireActivity(), "Maximum Attribute limit exceeded")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(requireActivity(), response.body()!!.data)

                        if (TextUtils.getString(statuesPayment).equals("0", ignoreCase = true)) {
                            binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.red_10_bg)
                            binding!!.paymentStatusTxt.text = "Cancel"
                        }
                        else if (TextUtils.getString(statuesPayment).equals("1", ignoreCase = true))
                        {
                            binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.green_10_bg)
                            //  binding.payment.setPadding(0, 0, 0, 0);
                            binding!!.paymentStatusTxt.text = "Complete"
                        }
                        else if (TextUtils.getString(statuesPayment).equals("2", ignoreCase = true))
                        {
                            binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.yellow_10_bg)
                            //  binding.payment.setPadding(0, 0, 0, 0);
                            binding!!.paymentStatusTxt.text = "Pending"
                        }  else if (TextUtils.getString(statuesPayment).equals("3", ignoreCase = true))
                        {
                            binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.red_10_bg)
                            //  binding.payment.setPadding(0, 0, 0, 0);
                            binding!!.paymentStatusTxt.text = "Incomplete"
                        }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                    apiCallPaymentChange()


                }

            })
    }

    companion object {
        var orderId = ""
        var statuesOrder = ""
        var statuesPayment = ""

    }

    private fun setData(data: OrderByID) {
        try {
            orderId = data.info.id.toString()
            // Log.e("orderId",data.orderContent.orderId)
            binding!!.parentView.visibility = View.VISIBLE
            binding!!.orderNoTv.text = "Order No:  " + TextUtils.getString(data.info.orderNo)
            binding!!.ShippingFee.text = "₹" + TextUtils.getString(data.info.shipping)
            binding!!.Tax.text = "₹" + TextUtils.getString(data.info.tax)
            binding!!.Discount.text =
                "₹" + TextUtils.getString(data.orderContent.couponDiscount)
            binding!!.Subtotal.text =
                "₹" + TextUtils.getString(data.orderContent.subTotal)
            binding!!.totalNew.text = "₹" + TextUtils.getString(data.info.total)
            binding!!.custmerStatus.text = TextUtils.getString(data.info.customer.name)
            val content = data.orderContent
            binding!!.name.text = TextUtils.getString(content.name)
            binding!!.email.text = TextUtils.getString(content.email)
            binding!!.phone.text = TextUtils.getString(content.phone)
            binding!!.zipcode.text = TextUtils.getString(content.zipCode)
            binding!!.address.text = TextUtils.getString(content.address)
            binding!!.transactionsIDTxt.text = TextUtils.getString(data.info.transactionId)
            orderList = data.info.orderItem
            binding!!.orderName.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
            orderAdapter = OrderInfoAdapter(context, orderList, 0)
            binding!!.orderName.adapter = orderAdapter

//            if (TextUtils.getString(data.info.paymentStatus).equals("1", ignoreCase = true)) {
//                binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.green_10_bg)
//                binding!!.paymentStatusTxt.text = "Completed"
//            } else if (TextUtils.getString(data.info.paymentStatus).equals("2", ignoreCase = true)) {
//                binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.yellow_10_bg)
//                //  binding.payment.setPadding(0, 0, 0, 0);
//                binding!!.paymentStatusTxt.text = "Pending"
//            }
//
//
//            if (TextUtils.getString(data.info.getway.toString()) == "null") {
//                binding!!.textView3.setBackgroundResource(R.drawable.yellow_10_bg)
//                binding!!.textView3.text = "Incomplete Payment"
//            } else if (TextUtils.getString(data.info.getway.toString()) == "1") {
//                binding!!.textView3.setBackgroundResource(R.drawable.green_10_bg)
//                //  binding.payment.setPadding(0, 0, 0, 0);
//                binding!!.textView3.text = "COD"
//            }
//            else if (TextUtils.getString(data.info.getway.toString()) == "2") {
//                binding!!.textView3.setBackgroundResource(R.drawable.green_10_bg)
//                //  binding.payment.setPadding(0, 0, 0, 0);
//                binding!!.textView3.text = "Mobile"
//            }



            if (TextUtils.getString(data.info.paymentStatus).equals("0", ignoreCase = true)) {
                binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.red_10_bg)
                binding!!.paymentStatusTxt.text = "Cancel"
            }
            else if (TextUtils.getString(data.info.paymentStatus).equals("1", ignoreCase = true))
            {
                binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.green_10_bg)
                //  binding.payment.setPadding(0, 0, 0, 0);
                binding!!.paymentStatusTxt.text = "Complete"
            }
            else if (TextUtils.getString(data.info.paymentStatus).equals("2", ignoreCase = true))
            {
                binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.yellow_10_bg)
                //  binding.payment.setPadding(0, 0, 0, 0);
                binding!!.paymentStatusTxt.text = "Pending"
            }  else if (TextUtils.getString(data.info.paymentStatus).equals("3", ignoreCase = true))
            {
                binding!!.paymentStatusTxt.setBackgroundResource(R.drawable.red_10_bg)
                //  binding.payment.setPadding(0, 0, 0, 0);
                binding!!.paymentStatusTxt.text = "Incomplete"
            }


            if (TextUtils.getString(data.info.status.toString()) == "completed") {
                binding!!.orderStatusC.text = "Competed"
                binding!!.orderStatusC.setBackgroundResource(R.drawable.green_10_bg)
            } else if (TextUtils.getString(data.info.status.toString()) == "pending") {
                binding!!.orderStatusC.text = "Awaiting processing"
                binding!!.orderStatusC.setBackgroundResource(R.drawable.yellow_10_bg)
            } else if (TextUtils.getString(data.info.status.toString()) == "archived") {
                binding!!.orderStatusC.text = "Archived"
                binding!!.orderStatusC.setBackgroundResource(R.drawable.yellow_10_bg)
            } else if (TextUtils.getString(data.info.status.toString()) == "canceled") {
                binding!!.orderStatusC.text = "Canceled"
                binding!!.orderStatusC.setBackgroundResource(R.drawable.red_10_bg)
            } else if (TextUtils.getString(data.info.status.toString()) == "ready-for-pickup") {
                binding.orderStatusC.text = "Ready for pickup"
                binding.orderStatusC.setBackgroundResource(R.drawable.sky_10_bg)
            } else if (TextUtils.getString(data.info.status.toString()) == "processing") {
                binding.orderStatusC.text = "Processing"
                binding.orderStatusC.setBackgroundResource(R.drawable.blue_10_bg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun paymentDialog(textView: TextView) {
        val builderSingle = AlertDialog.Builder(context)
        builderSingle.setTitle("Select Payment Status")
        val arrayAdapter = ArrayAdapter<String>(activity!!, R.layout.dialog_txt_item)
        arrayAdapter.add("Cancel")
        arrayAdapter.add("Complete")
        arrayAdapter.add("Pending")
        arrayAdapter.add("Incomplete")
        builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
        builderSingle.setAdapter(arrayAdapter) { dialog, which ->
            val strName = arrayAdapter.getItem(which)
            statuesPayment = which.toString()

            Log.e("", statuesPayment)
            binding.btn1.text="Save Payment Status"
            binding.btn1.isEnabled=true

            textView.text = strName
        }
        builderSingle.show()
    }

    private fun filterDialog(textView: TextView) {
        val builderSingle = AlertDialog.Builder(context)
        builderSingle.setTitle("Select fulfillment")
        val arrayAdapter = ArrayAdapter<String>(activity!!, R.layout.dialog_txt_item)
        arrayAdapter.add("Awaiting processing")
        arrayAdapter.add("Processing")
        arrayAdapter.add("Ready-for-pickup")
        arrayAdapter.add("Completed")
        arrayAdapter.add("Archived")
        arrayAdapter.add("Canceled")
        builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
        builderSingle.setAdapter(arrayAdapter) { dialog, which ->
            val strName = arrayAdapter.getItem(which)

            statuesOrder = which.toString()
            when (which) {
                0 -> statuesOrder = "pending"
                1 -> statuesOrder = "processing"
                2 -> statuesOrder = "ready-for-pickup"
                3 -> statuesOrder = "completed"
                4 -> statuesOrder = "archived"
                5 -> statuesOrder = "canceled"
            }
            binding.btn1.text="Save Order Status"
            binding.btn1.isEnabled=true
            Log.e("Statues", statuesOrder.toString())
            textView.text = strName
        }
        builderSingle.show()
    }
}