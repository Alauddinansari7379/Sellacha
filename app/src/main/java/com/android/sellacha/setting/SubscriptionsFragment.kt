package com.android.sellacha.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.OfferAndAds.model.DataX
import com.android.sellacha.OfferAndAds.model.ModelCreateAd
import com.android.sellacha.Products.Brands.adapter.AdapterBrand
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentCreateBannerAdBinding
import com.android.sellacha.databinding.FragmentCreateBumpAdBinding
import com.android.sellacha.databinding.FragmentPaymentOptionsBinding
import com.android.sellacha.databinding.FragmentSubscriptionBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.setting.adapter.AdapterSubscription
import com.android.sellacha.setting.model.Data
import com.android.sellacha.setting.model.ModelSubscription
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SubscriptionsFragment : Fragment(){
    var binding: FragmentSubscriptionBinding? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null
    var url="#"
    var mainData=ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subscription, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubscriptionBinding.bind(view)

        sessionManager = SessionManager(requireContext())

        apiCallSubscription()

    }

    private fun apiCallSubscription() {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.planDetails(sessionManager.authToken)
            .enqueue(object : Callback<ModelSubscription> {
                override fun onResponse(
                    call: Call<ModelSubscription>, response: Response<ModelSubscription>
                ) {
                    try {
                        if (response.code() == 404) {
                            myToast(requireActivity(), "Something went wrong")
                            AppProgressBar.hideLoaderDialog()

                        }
                        else if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")

                        } else if (response.body()!!.data.isEmpty()) {
                            mainData=response.body()!!.data
                            binding!!.recyclerView.adapter =
                                activity?.let { AdapterSubscription(it, mainData)
                                }
                            binding!!.recyclerView.adapter!!.notifyDataSetChanged()
                            myToast(requireActivity(), "No Brand Found")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            mainData=response.body()!!.data

                            binding!!.recyclerView.adapter =
                                activity?.let { AdapterSubscription(it,mainData)
                                }
                            AppProgressBar.hideLoaderDialog()


                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelSubscription>, t: Throwable) {
                    //myToast(requireActivity(), "Something went wrong")
                    apiCallSubscription()
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }


}