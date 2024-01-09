package com.android.sellacha.marketingTools

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.android.sellacha.Order.Model.ModelCoupon
import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.databinding.FragmentFacebookPixelBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.marketingTools.model.ModelFacebookPixel
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FacebookPixelFragment : Fragment() {
    lateinit var binding: FragmentFacebookPixelBinding
    lateinit var sessionManager: SessionManager
    var statuseList = ArrayList<ModelProductType>()
    var status = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_facebook_pixel, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFacebookPixelBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        apiCallGetFacebokPixel()
        binding.saveBtn.setOnClickListener {
            if (binding.txtYourPixelID.text.isEmpty()) {
                binding.txtYourPixelID.error = "Enter Facebook Pixel ID"
                binding.txtYourPixelID.requestFocus()
                return@setOnClickListener
            }
            apiCallFacebookPixel()
        }


        statuseList.add(ModelProductType("Enable", 1))
        statuseList.add(ModelProductType("Disable", 0))

        binding!!.txtStatus.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            statuseList
        )


        binding!!.txtStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (statuseList.size > 0) {
                        status = statuseList[i].value.toString()


                        //  Log.e(ContentValues.TAG, "statuse: $statuse")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }


    }


    private fun apiCallFacebookPixel() {

        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.marketing(
            sessionManager.authToken,
            "fb_pixel",
            binding.txtYourPixelID.text.toString().trim(),
            status
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
                            binding.txtYourPixelID.text.clear()
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
                    apiCallFacebookPixel()

                }

            })
    }
    private fun apiCallGetFacebokPixel() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.marketingGetFaceBook(
            sessionManager.authToken,
            "facebook-pixel",
        )
            .enqueue(object : Callback<ModelFacebookPixel> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelFacebookPixel>, response: Response<ModelFacebookPixel>
                ) {
                    try {
                        if (response.code() == 404) {
                            myToast(requireActivity(), "Something went Wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding.txtYourPixelID.setText(response.body()!!.data.fb_pixel.value)
                            AppProgressBar.hideLoaderDialog()

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        myToast(requireActivity(), "Something went Wrong")
                        AppProgressBar.hideLoaderDialog()

                    }
                }


                override fun onFailure(call: Call<ModelFacebookPixel>, t: Throwable) {
                    //  myToast(this@HomeFragment, t.message.toString())
                    apiCallGetFacebokPixel()

                }

            })
    }

}
