package com.android.sellacha.Order.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.android.sellacha.Order.Model.*
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.createOrder.adapter.AdapterCart
import com.android.sellacha.databinding.ActivityCheckOutBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.StatusBarUtils
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CheckOut : AppCompatActivity(), AdapterCart.RemoveCart {
    var binding: ActivityCheckOutBinding? = null
    lateinit var sessionManager: SessionManager
    var deliveryTypeLIst = ArrayList<ModelProductType>()
    var customerTypeLIst = ArrayList<ModelProductType>()
    var deliveryTyp = ""
    var customerType = ""
    var location = ""
    var shipping = ""
    private var paymentMethod = ""
    var paymentStatues = ""
    private var mainData = ArrayList<DataX>()
    private var mainDataShipping = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_out)
        StatusBarUtils.transparentStatusAndNavigation(this)
        sessionManager = SessionManager(this@CheckOut)

        apiGetCart()
        apiCallLocation()


        deliveryTypeLIst.add(ModelProductType("Hand Over Delivery", 1))
        deliveryTypeLIst.add(ModelProductType("Virtual Delivery (Virtual Products)", 0))

        customerTypeLIst.add(ModelProductType("Guest Customer", 0))
        customerTypeLIst.add(ModelProductType("Website Customer", 1))



        with(binding!!) {

            makeOrderBtn.setOnClickListener {
                if (radioMobileButton.isChecked) {
                    paymentMethod = "1"
                }
                if (radioCashButton.isChecked) {
                    paymentMethod = "2"
                }

                if (radioPaySCom.isChecked) {
                    paymentStatues = "1"
                }
                if (radioPaySPen.isChecked) {
                    paymentStatues = "2"
                }
                if (customerNameLb.text!!.isEmpty()) {
                    customerNameLb.error = "Enter Customer Name"
                    customerNameLb.requestFocus()
                    return@setOnClickListener
                }
                if (customerEmailLb.text!!.isEmpty()) {
                    customerEmailLb.error = "Enter Customer Email"
                    customerEmailLb.requestFocus()
                    return@setOnClickListener
                }
                if (customerPhoneLb.text!!.isEmpty()) {
                    customerPhoneLb.error = "Enter Customer Phone"
                    customerPhoneLb.requestFocus()
                    return@setOnClickListener
                }
                if (addressLb.text!!.isEmpty()) {
                    addressLb.error = "Enter Address"
                    addressLb.requestFocus()
                    return@setOnClickListener
                }

                if (shipping.isEmpty()) {
                    myToast(this@CheckOut,"Please Select Shipping")
                      return@setOnClickListener
                }
                apiMakeOrder()

            }



            redeenBtn.setOnClickListener {
                apiApplyCoupon()
            }
            backBtn.setOnClickListener {
                onBackPressed()
            }
            deliveryTypeLb.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        adapterView: AdapterView<*>?,
                        view: View?,
                        i: Int,
                        l: Long
                    ) {
                        if (deliveryTypeLIst.size > 0) {
                            deliveryTyp = deliveryTypeLIst[i].value.toString()

                            Log.e(ContentValues.TAG, "deliveryTyp: $deliveryTyp")
                        }
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                }

            customerTypeLb.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        adapterView: AdapterView<*>?,
                        view: View?,
                        i: Int,
                        l: Long
                    ) {
                        if (customerTypeLIst.size > 0) {
                            customerType = customerTypeLIst[i].value.toString()

                            Log.e(ContentValues.TAG, "customerType: $customerType")
                        }
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                }

            deliveryTypeLb!!.adapter = ArrayAdapter<ModelProductType>(
                this@CheckOut,
                R.layout.simple_list_item_1,
                deliveryTypeLIst
            )

            customerTypeLb!!.adapter = ArrayAdapter<ModelProductType>(
                this@CheckOut,
                R.layout.simple_list_item_1,
                customerTypeLIst
            )

        }

    }

    private fun apiApplyCoupon() {
        AppProgressBar.showLoaderDialog(this@CheckOut)

        ApiClient.apiService.applyCoupon(
            sessionManager.authToken,
            binding!!.textView19.text.toString()
        )
            .enqueue(object : Callback<ModelCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupon>, response: Response<ModelCoupon>
                ) {
                    if (response.code() == 500) {
                        myToast(this@CheckOut, "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.code() == 404) {
                        myToast(this@CheckOut, "Coupon Code Not Found.")
                        binding!!.textView19.setTextColor(Color.parseColor("#FF0000"))
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(this@CheckOut, response.body()!!.data)
                        binding!!.textView19.setTextColor(Color.parseColor("#008000"))
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                    apiApplyCoupon()
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun apiCallDestroyCart() {
        AppProgressBar.showLoaderDialog(this@CheckOut)

        ApiClient.apiService.destroyCart(
            sessionManager.authToken,
        )
            .enqueue(object : Callback<ModelCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupon>, response: Response<ModelCoupon>
                ) {
                    if (response.code() == 500) {
                        myToast(this@CheckOut, "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.code() == 404) {
                        myToast(this@CheckOut, "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(this@CheckOut, response.body()!!.data)
                        onBackPressed()
//                        val intent = Intent(
//                            this@CheckOut,
//                            OrderFragment::class.java)
//                        startActivity(intent)
//                        //Navigation.findNavController(binding!!.root).navigate(R.id.orderFragment)
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                    apiCallDestroyCart()
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun apiGetCart() {
        AppProgressBar.showLoaderDialog(this@CheckOut)

        ApiClient.apiService.getCart(sessionManager.authToken)
            .enqueue(object : Callback<ModelGetCart> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelGetCart>, response: Response<ModelGetCart>
                ) {
                    try {

                        if (response.code() == 500) {
                            myToast(this@CheckOut, "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.body()!!.data.items.isEmpty()) {
                            binding!!.recyclerViewCart.adapter =
                                AdapterCart(
                                    this@CheckOut,
                                    response.body()!!.data.items,
                                    this@CheckOut
                                )
                            myToast(this@CheckOut, "No Item Found in Cart")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding!!.recyclerViewCart.adapter =
                                AdapterCart(
                                    this@CheckOut,
                                    response.body()!!.data.items,
                                    this@CheckOut)



                            AppProgressBar.hideLoaderDialog()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelGetCart>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiGetCart()
                }

            })
    }

    private fun apiCallLocation() {
        AppProgressBar.showLoaderDialog(this@CheckOut)

        ApiClient.apiService.getLocation(sessionManager.authToken)
            .enqueue(object : Callback<ModelCategory> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCategory>, response: Response<ModelCategory>
                ) {
                    try {

                        if (response.code() == 500) {
                            myToast(this@CheckOut, "Server Error")

                        } else if (response.body()!!.data.posts.data.isEmpty()) {
                            AppProgressBar.hideLoaderDialog()

                        }
                        if (response.code() == 200) {
                            mainData = response.body()!!.data.posts.data
                            mainData = response.body()!!.data.posts.data
                            if (mainData != null) {

                                //spinner code start
                                val items = arrayOfNulls<String>(mainData.size)

                                for (i in mainData!!.indices) {

                                    items[i] = mainData!![i].name
                                }
                                val adapter: ArrayAdapter<String?> =
                                    ArrayAdapter(this@CheckOut, R.layout.simple_list_item_1, items)
                                binding!!.locationLb.adapter = adapter
                                //   binding.spinnerLocation.setSelection(items.indexOf(sessionManager.qualification.toString()));
                                //   binding.spinnerDegree.setSelection(sessionManager.qualification.toString().toInt())
                                AppProgressBar.hideLoaderDialog()


                                binding!!.locationLb.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            adapterView: AdapterView<*>?,
                                            view: View,
                                            i: Int,
                                            l: Long
                                        ) {
                                            location = mainData!![i].id.toString()
                                            apiCallShippingCity(location)
                                            // Toast.makeText(this@RegirstrationTest, "" + id, Toast.LENGTH_SHORT).show()
                                        }

                                        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                    }

                            }
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallLocation()
                }

            })
    }

    private fun apiCallShippingCity(id: String) {
        AppProgressBar.showLoaderDialog((this@CheckOut))
        ApiClient.apiService.getShippingCity(sessionManager.authToken, id.toString())
            .enqueue(object : Callback<ModelShippingCiry> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelShippingCiry>, response: Response<ModelShippingCiry>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(this@CheckOut, "Server Error")
                        } else if (response.body()!!.data.posts.isEmpty()) {
                            AppProgressBar.hideLoaderDialog()
                        }
                        if (response.code() == 200) {
                            mainDataShipping = response.body()!!.data.posts
                            if (mainDataShipping != null) {

                                //spinner code start
                                val items = arrayOfNulls<String>(mainDataShipping.size)

                                for (i in mainDataShipping!!.indices) {

                                    items[i] = mainDataShipping!![i].name
                                }
                                val adapter: ArrayAdapter<String?> =
                                    ArrayAdapter(this@CheckOut, R.layout.simple_list_item_1, items)
                                binding!!.shippingSpinner.adapter = adapter
                                //   binding.spinnerLocation.setSelection(items.indexOf(sessionManager.qualification.toString()));
                                //   binding.spinnerDegree.setSelection(sessionManager.qualification.toString().toInt())
                                AppProgressBar.hideLoaderDialog()


                                binding!!.shippingSpinner.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            adapterView: AdapterView<*>?,
                                            view: View,
                                            i: Int,
                                            l: Long
                                        ) {
                                            shipping = mainDataShipping!![i].id.toString()
                                            // Toast.makeText(this@RegirstrationTest, "" + id, Toast.LENGTH_SHORT).show()
                                        }

                                        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                    }

                            }
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelShippingCiry>, t: Throwable) {
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallShippingCity(id)
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }


    private fun apiMakeOrder() {
        AppProgressBar.showLoaderDialog(this@CheckOut)

        ApiClient.apiService.makeOrder(
            sessionManager.authToken,
            customerType,
            deliveryTyp,
            shipping,
            binding!!.paymentIdLb.text.toString(),
            paymentMethod,
            paymentStatues,
            binding!!.customerNameLb.text.toString(),
            binding!!.customerEmailLb.text.toString(),
            binding!!.customerPhoneLb.text.toString(),
            binding!!.orderNoteLb.text.toString(),
            binding!!.addressLb.text.toString(),
            binding!!.zipLb.text.toString(),
            location,
        )
            .enqueue(object : Callback<ModelCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupon>, response: Response<ModelCoupon>
                ) {
                    if (response.code() == 500) {
                        myToast(this@CheckOut, "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.code() == 404) {
                        myToast(this@CheckOut, "Something went wrong")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(this@CheckOut, response.body()!!.data)
                        apiCallDestroyCart()
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                    myToast(this@CheckOut, "Try Again")

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    override fun removeCart(id: String) {
        AppProgressBar.showLoaderDialog(this@CheckOut)
        ApiClient.apiService.cartRemove(
            sessionManager.authToken,
            id
        )
            .enqueue(object : Callback<ModelCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupon>, response: Response<ModelCoupon>
                ) {
                    if (response.code() == 500) {
                        myToast(this@CheckOut, "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.code() == 404) {
                        myToast(this@CheckOut, "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(this@CheckOut, response.body()!!.data)
                        refresh()
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                    removeCart(id)
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    @SuppressLint("SetTextI18n")
    override fun totalAmount(finerTotal: String) {
        binding!!.finleTotal.text = "₹$finerTotal"
     }

    private fun refresh() {
        overridePendingTransition(0,0)
        startActivity(intent)
        overridePendingTransition(0,0)

    }

}