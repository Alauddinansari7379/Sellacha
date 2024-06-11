package com.android.sellacha.setting.Payment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentPaymentOptionsBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.setting.Payment.Model.ModelPaymentList
import com.android.sellacha.sharedpreferences.SessionManager
import com.android.sellacha.utils.AppProgressBar
import com.bumptech.glide.Glide
import com.example.myrecyview.apiclient.ApiClient
import com.squareup.picasso.Picasso
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentOptionsFragment : Fragment() {
    var binding: FragmentPaymentOptionsBinding? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null
    var url = "#"
    var cOD = false
    var alt = false
    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment_options, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentOptionsBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        with(binding!!) {
            cardInstamojo.setOnClickListener {
                Navigation.findNavController(binding!!.root).navigate(R.id.Instamojo)
            }
            cardPaypal.setOnClickListener {
                Navigation.findNavController(binding!!.root).navigate(R.id.Paypal)
            }

            cardRazorpay.setOnClickListener {
                Navigation.findNavController(binding!!.root).navigate(R.id.Razorpay)
            }

            btnManual.setOnClickListener {
                if (cOD) {
                    cOD = false
                    layoutCOD.visibility = View.GONE
                    layoutAlternative.visibility = View.GONE

                } else {
                    cOD = true
                    layoutCOD.visibility = View.VISIBLE
                    layoutAlternative.visibility = View.GONE
                }
            }
            btnAlternative.setOnClickListener {
                if (alt) {
                    alt = false
                    layoutAlternative.visibility = View.GONE
                    layoutCOD.visibility = View.GONE

                } else {
                    alt = true
                    layoutAlternative.visibility = View.VISIBLE
                    layoutCOD.visibility = View.GONE
                }
            }

            apiCalLoginNimbus()
        }


    }

    private fun apiCalLoginNimbus() {
        AppProgressBar.showLoaderDialog(activity)


        ApiClient.apiService.paymentList(
            sessionManager.authToken.toString(),
        )
            .enqueue(object : Callback<ModelPaymentList> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelPaymentList>, response: Response<ModelPaymentList>
                ) {
                    try {
                        if (response.code() == 500) {
                            activity?.let { myToast(it, "Server Error") }
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            activity?.let { myToast(it, "Something went wrong") }
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            var imagePath = ""
                            for (i in response.body()!!.data.cod) {
                                val jsonString = i.active_getway.content
                                val jsonObject = JSONObject(jsonString)
                                imagePath = jsonObject.getString("file_name")
                            }
                            Picasso.get().load("https://storefront.sellacha.com/"+imagePath).into(binding!!.imgQR)

                            AppProgressBar.hideLoaderDialog()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        activity?.let { myToast(it, "Something went wrong") }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelPaymentList>, t: Throwable) {
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