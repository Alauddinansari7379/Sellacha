package com.android.sellacha.Order.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sellacha.Order.Model.ModelCreateShow
import com.android.sellacha.Order.adapter.AdapterCrreateShow
import com.android.sellacha.Products.Inventory.Model.Modelinventory
import com.android.sellacha.Products.Inventory.adapter.AdapterInventory
import com.android.sellacha.R
import com.android.sellacha.activity.CheckOut
import com.android.sellacha.adapter.ProductAdapter
import com.android.sellacha.adapter.ProductFilterSelector
import com.android.sellacha.api.ApiResponse
import com.android.sellacha.api.model.filterItemsDM
import com.android.sellacha.api.response.Product.DataItem
import com.android.sellacha.api.response.Product.GetAllProduct
import com.android.sellacha.api.service.MainService
import com.android.sellacha.databinding.FragmentCreateOrderShowBinding
import com.android.sellacha.databinding.FragmentProductBinding
import com.android.sellacha.dialog.AppDialog
import com.android.sellacha.fragment.BaseFragment
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import com.google.gson.Gson
import com.google.gson.JsonNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CreateOrderShowFragment : BaseFragment(),AdapterCrreateShow.AddProduct {
    var binding: FragmentCreateOrderShowBinding? = null
    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_order_show, container, false)
        sessionManager = SessionManager(requireContext())
        apiCallOrderShow()


        binding!!.imRefresh.setOnClickListener {
            apiCallOrderShow()
        }
        binding!!.Proceed.setOnClickListener {
             startActivity(Intent(mContext, CheckOut::class.java))

        }
        binding!!.imgSearch.setOnClickListener {
            if (binding!!.edtId.text.isEmpty()){
                binding!!.edtId.error="Enter Product Id"
                binding!!.edtId.requestFocus()
                return@setOnClickListener
            }
            apiCallOrderShowSearch()
        }
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
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding!!.inventoryRv.adapter =
                            activity?.let {

                                AdapterCrreateShow(it, response.body()!!,this@CreateOrderShowFragment)
                            }
                        binding!!.inventoryRv.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Data Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.inventoryRv.adapter =
                            activity?.let {
                                AdapterCrreateShow(it, response.body()!!,this@CreateOrderShowFragment)

                            }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCreateShow>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallOrderShow()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
    private fun apiCallOrderShowSearch() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.productSearch(sessionManager.authToken,binding!!.edtId.text.toString(),"title")
            .enqueue(object : Callback<ModelCreateShow> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateShow>, response: Response<ModelCreateShow>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding!!.inventoryRv.adapter =
                            activity?.let {

                                AdapterCrreateShow(it, response.body()!!,this@CreateOrderShowFragment)
                            }
                        binding!!.inventoryRv.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Data Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.inventoryRv.adapter =
                            activity?.let {
                                AdapterCrreateShow(it, response.body()!!,this@CreateOrderShowFragment)

                            }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCreateShow>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallOrderShowSearch()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    override fun addItem() {
        TODO("Not yet implemented")
    }


}