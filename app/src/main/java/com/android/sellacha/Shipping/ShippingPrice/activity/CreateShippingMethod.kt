package com.android.sellacha.Shipping.ShippingPrice.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.R
import com.android.sellacha.Shipping.ShippingPrice.model.ModelCreateShipping
import com.android.sellacha.databinding.FragmentCreateShippingMethodBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateShippingMethod : Fragment() {
    lateinit var binding: FragmentCreateShippingMethodBinding
    private lateinit var sessionManager: SessionManager
    var locationlist = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_shipping_method, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateShippingMethodBinding.bind(view)
        sessionManager = SessionManager(requireContext())



        binding.btnSave.setOnClickListener {
            if (binding.txtTitle.text.toString().isEmpty()) {
                binding.txtTitle.error = "Enter Title"
                binding.txtTitle.requestFocus()
                return@setOnClickListener
            }
            if (binding.txtPrice.text.toString().isEmpty()) {
                binding.txtPrice.error = "Enter Price"
                binding.txtPrice.requestFocus()
                return@setOnClickListener
            }
            if (binding.txtLocation.text.toString().isEmpty()) {
                binding.txtLocation.error = "Enter Location"
                binding.txtLocation.requestFocus()
                return@setOnClickListener
            }
            val tile = binding.txtTitle.text.toString().trim()
            val price = binding.txtPrice.text.toString().trim()
            locationlist.add(binding.txtLocation.text.toString().trim())

            apiCallCreateShipping(tile, price)
        }


    }

    private fun apiCallCreateShipping(tile: String, price: String,) {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.createShipping(sessionManager.authToken, tile, price, locationlist)
            .enqueue(object : Callback<ModelCreateShipping> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateShipping>, response: Response<ModelCreateShipping>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Created")
                        binding.txtTitle.text.clear()
                        binding.txtPrice.text.clear()
                        binding.txtLocation.text.clear()
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(requireActivity(), response.message())
                        binding.txtTitle.text.clear()
                        binding.txtPrice.text.clear()
                        binding.txtLocation.text.clear()
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelCreateShipping>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
}