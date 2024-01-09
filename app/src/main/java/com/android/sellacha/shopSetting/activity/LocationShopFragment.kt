package com.android.sellacha.shopSetting.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.Order.Model.ModelCoupon
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentLoactionShopBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationShopFragment : Fragment(){
    lateinit var binding: FragmentLoactionShopBinding
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loaction_shop, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoactionShopBinding.bind(view)
        sessionManager = SessionManager(requireContext())


        binding!!.saveBtn.setOnClickListener {
            if (binding.Company.text!!.isEmpty()) {
                binding.Company.error = "Enter Company Name"
                binding.Company.requestFocus()
                return@setOnClickListener
            }
            if (binding.Address.text!!.isEmpty()) {
                binding.Address.error = "Enter Address"
                binding.Address.requestFocus()
                return@setOnClickListener
            }
            if (binding.City.text!!.isEmpty()) {
                binding.City.error = "Enter City"
                binding.City.requestFocus()
                return@setOnClickListener
            }
            if (binding.ZipCode.text!!.isEmpty()) {
                binding.ZipCode.error = "Enter ZipCode"
                binding.ZipCode.requestFocus()
                return@setOnClickListener
            }
            if (binding.Email.text!!.isEmpty()) {
                binding.Email.error = "Enter Email"
                binding.Email.requestFocus()
                return@setOnClickListener
            }
            if (binding.Phone.text!!.isEmpty()) {
                binding.Phone.error = "Enter Phone"
                binding.Phone.requestFocus()
                return@setOnClickListener
            }
            if (binding.InvoiceDes.text!!.isEmpty()) {
                binding.InvoiceDes.error = "Enter TaInvoice Description"
                binding.InvoiceDes.requestFocus()
                return@setOnClickListener
            }
            apiCallLocation()

        }


    }

    private fun apiCallLocation() {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.themeSettingsLocation(
            sessionManager.authToken,
            binding.Company.text.toString().trim(),
            "location",
            binding.Address.text.toString().trim(),
            binding.City.text.toString().trim(),
            binding.State.text.toString().trim(),
            binding.ZipCode.text.toString().trim(),
            binding.Email.text.toString().trim(),
            binding.Phone.text.toString().trim(),
            binding.InvoiceDes.text.toString().trim(),
               )
            .enqueue(object : Callback<ModelCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupon>, response: Response<ModelCoupon>
                ) {
                    try {
                        if (response.code() == 404) {
                            myToast(requireActivity(), "Something went Wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            myToast(requireActivity(), response.body()!!.data)
                            AppProgressBar.hideLoaderDialog()

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        myToast(requireActivity(), "Something went Wrong")
                        AppProgressBar.hideLoaderDialog()

                    }
                }


                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                    //  myToast(this@HomeFragment, t.message.toString())
                    apiCallLocation()

                }

            })
    }


}