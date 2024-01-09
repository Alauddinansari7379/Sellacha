package com.android.sellacha.Order.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.android.sellacha.Order.Model.DataX
import com.android.sellacha.Order.Model.ModelAddToCart
import com.android.sellacha.Order.Model.ModelCreateShow
import com.android.sellacha.R
import com.android.sellacha.Order.adapter.AdapterCrreateShow
import com.android.sellacha.databinding.FragmentCreateOrderShowBinding
import com.android.sellacha.fragment.BaseFragment
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CreateOrderShowFragment : BaseFragment(), AdapterCrreateShow.AddProduct {
    var binding: FragmentCreateOrderShowBinding? = null
    lateinit var sessionManager: SessionManager

    private var mainData = ArrayList<DataX>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_order_show, container, false)
        sessionManager = SessionManager(requireContext())
        apiCallOrderShow()


        binding!!.Proceed.setOnClickListener {
             startActivity(Intent(mContext, CheckOut::class.java))
        }

        binding!!.searchTxt.addTextChangedListener { str ->
            setRecyclerViewAdapter(mainData.filter {
                it!!.title!!.contains(
                    str.toString(),
                    ignoreCase = true
                )
            } as java.util.ArrayList<DataX>)
        }
     /*   binding!!.imgSearch.setOnClickListener {
            if (binding!!.edtId.text.isEmpty()){
                binding!!.edtId.error="Enter Product Name"
                binding!!.edtId.requestFocus()
                return@setOnClickListener
            }
            apiCallOrderShowSearch()
        }*/
        return binding!!.root


    }

    private fun apiCallOrderShow() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.createShow(sessionManager.authToken)
            .enqueue(object : Callback<ModelCreateShow> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateShow>, response: Response<ModelCreateShow>
                ) {
                    try {
                        if (response.code() == 200) {
                            mainData = response.body()!!.data.posts.data
                            AppProgressBar.hideLoaderDialog()

                        }
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.body()!!.data.posts.data.isEmpty()) {
                            binding!!.inventoryRv.adapter =
                                activity?.let {

                                    AdapterCrreateShow(
                                        it,
                                        response.body()!!.data.posts.data,
                                        this@CreateOrderShowFragment
                                    )
                                }
                             myToast(requireActivity(), "No Data Found")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding!!.inventoryRv.adapter =
                                activity?.let {
                                    AdapterCrreateShow(
                                        it,
                                        response.body()!!.data.posts.data,
                                        this@CreateOrderShowFragment
                                    )

                                }
                            AppProgressBar.hideLoaderDialog()
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelCreateShow>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallOrderShow()
                }

            })
    }

    private fun setRecyclerViewAdapter(data: java.util.ArrayList<DataX>) {
        binding!!.inventoryRv.apply {
            adapter = context?.let { AdapterCrreateShow(requireContext(), data,this@CreateOrderShowFragment) }
        }
    }


    override fun addItem(termId: String, qty: String) {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.addToCart(sessionManager.authToken,termId,qty)
            .enqueue(object : Callback<ModelAddToCart> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelAddToCart>, response: Response<ModelAddToCart>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        }  else if (response.code() == 404) {
                             myToast(requireActivity(), "Something went wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            myToast(requireActivity(), "Item Added In Cart")
                            AppProgressBar.hideLoaderDialog()
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelAddToCart>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    //apiCallOrderShowSearch()
                    addItem(termId,qty)
                    AppProgressBar.hideLoaderDialog()


                }

            })    }


}