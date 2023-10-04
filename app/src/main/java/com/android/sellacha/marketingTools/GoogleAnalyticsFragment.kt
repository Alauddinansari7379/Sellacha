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
import com.android.sellacha.databinding.FragmentGoogleAnalyticsBinding
import com.android.sellacha.databinding.FragmentGoogleTapManagerBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.marketingTools.model.ModelGoolgeAna
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GoogleAnalyticsFragment : Fragment() {
    lateinit var binding: FragmentGoogleAnalyticsBinding
    lateinit var sessionManager: SessionManager
    var statuseList = ArrayList<ModelProductType>()
    var status = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_google_analytics, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGoogleAnalyticsBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        apiCallGetAnalytics()
        binding.saveBtn.setOnClickListener {
            if (binding.txtMeasurmentId.text.isEmpty()){
                binding.txtMeasurmentId.error="Enter Measurement Id"
                binding.txtMeasurmentId.requestFocus()
                return@setOnClickListener
            }
            if (binding.txtAnalyticId.text.isEmpty()){
                binding.txtAnalyticId.error="Enter Tag Analytic ID"
                binding.txtAnalyticId.requestFocus()
                return@setOnClickListener
            }
            apiCallAnalytics()
        }


        statuseList.add(ModelProductType("Enable", 1))
        statuseList.add(ModelProductType("Disable", 0))

        binding!!.txtSatus.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            statuseList
        )


        binding!!.txtSatus.onItemSelectedListener =
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


    private fun apiCallAnalytics() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.marketingGoogleAnalytics(
            sessionManager.authToken,
            "google-analytics",
            binding.txtMeasurmentId.text.toString().trim(),
            binding.txtAnalyticId.text.toString().trim(),
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
                            binding.txtMeasurmentId.text.clear()
                            binding.txtAnalyticId.text.clear()
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
                    apiCallAnalytics()

                }

            })
    }
    private fun apiCallGetAnalytics() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.marketingGetAnalytics(
            sessionManager.authToken,
            "google-analytics",
         )
            .enqueue(object : Callback<ModelGoolgeAna> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelGoolgeAna>, response: Response<ModelGoolgeAna>
                ) {
                    try {
                        if (response.code() == 404) {
                            myToast(requireActivity(), "Something went Wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                             binding.txtMeasurmentId.setText(response.body()!!.data.info.ga_measurement_id)
                            binding.txtAnalyticId.setText(response.body()!!.data.info.analytics_view_id)
                             AppProgressBar.hideLoaderDialog()

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        myToast(requireActivity(), "Something went Wrong")
                        AppProgressBar.hideLoaderDialog()

                    }
                }


                override fun onFailure(call: Call<ModelGoolgeAna>, t: Throwable) {
                    //  myToast(this@HomeFragment, t.message.toString())
                    apiCallAnalytics()

                }

            })
    }

}