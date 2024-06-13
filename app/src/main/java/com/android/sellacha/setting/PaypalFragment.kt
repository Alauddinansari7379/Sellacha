package com.android.sellacha.setting

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentPaypalBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.setting.Payment.Model.ModelPaymentList
import com.android.sellacha.setting.Payment.ModelAddPayment.ModelAddPayment
import com.android.sellacha.sharedpreferences.SessionManager
import com.android.sellacha.utils.AppProgressBar
import com.example.myrecyview.apiclient.ApiClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaypalFragment : Fragment() {
    var binding: FragmentPaypalBinding? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null
    var url = "#"
    var count = 0
    private var statues = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_paypal, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaypalBinding.bind(view)

        sessionManager = SessionManager(requireContext())


        with(binding) {
            this?.saveBtn!!.setOnClickListener {
                if (edtTitle.text.toString().isEmpty()) {
                    edtTitle.error = "Enter Display Name"
                    edtTitle.requestFocus()
                    return@setOnClickListener
                }

                if (edTCurrency.text.toString().isEmpty()) {
                    edTCurrency.error = "Enter Currency"
                    edTCurrency.requestFocus()
                    return@setOnClickListener
                }
                if (edtClintID.text.toString().isEmpty()) {
                    edtClintID.error = "Enter ClintID"
                    edtClintID.requestFocus()
                    return@setOnClickListener
                }

                if (edtClintSecret.text.toString().isEmpty()) {
                    edtClintSecret.error = "Enter ClintSecret"
                    edtClintSecret.requestFocus()
                    return@setOnClickListener
                }
                apiCallAddPaymentDetail()
            }


            SwitchEna.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    statues="1"
                } else {
                    statues="0"

                }
            }
        }


        apiCallGetPaymentDet()
    }

    private fun apiCallGetPaymentDet() {
        AppProgressBar.showLoaderDialog(activity)
        ApiClient.apiService.paymentList(
            sessionManager.authToken.toString()
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
                            if (response.body()!!.data.posts.isEmpty()) {
                                activity?.let { myToast(it, "Not found") }
                                AppProgressBar.hideLoaderDialog()

                            } else {
                                for (i in response.body()!!.data.posts) {
                                    var title = ""
                                    var currency = ""
                                    var edtClintID = ""
                                    var edtClintSecret = ""
                                    var purpose = ""
                                    var status = 0
                                    if (i.active_getway.id == 32) {
                                        val jsonString = i.active_getway.content
                                        val jsonObject = JSONObject(jsonString)
                                        title = jsonObject.getString("title")
                                        currency = jsonObject.getString("currency")
                                        edtClintID = jsonObject.getString("ClientID")
                                        edtClintSecret = jsonObject.getString("ClientSecret")
                                        purpose = jsonObject.getString("env")
                                        status = i.active_getway.status
                                        binding!!.edtTitle.setText(title)
                                        binding!!.edTCurrency.setText(currency)
                                        binding!!.edtClintID.setText(edtClintID)
                                        binding!!.edtClintSecret.setText(edtClintSecret)
                                        if (status == 1) {
                                            binding!!.SwitchEna.isChecked = true
                                        } else {
                                            binding!!.SwitchEna.isChecked = false

                                        }

                                    }
                                }
                                AppProgressBar.hideLoaderDialog()


                            }

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        //  activity?.let { myToast(it, "Something went wrong") }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelPaymentList>, t: Throwable) {
                    count++
                    if (count <= 3) {
                        Log.e("count", count.toString())
                        apiCallGetPaymentDet()
                    } else {
                        activity?.let { myToast(it, t.message.toString()) }
                        AppProgressBar.hideLoaderDialog()

                    }
                    AppProgressBar.hideLoaderDialog()
                }


            })
    }

    private fun apiCallAddPaymentDetail() {
        AppProgressBar.showLoaderDialog(activity)


        ApiClient.apiService.addPaymentDetail(
            sessionManager.authToken.toString(),
            "32",
            "",
            binding!!.edtTitle.text.toString(),
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            binding!!.edTCurrency.text.toString(),
            binding!!.edtClintID.text.toString(),
            binding!!.edtClintSecret.text.toString(),
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            statues,
        )
            .enqueue(object : Callback<ModelAddPayment> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelAddPayment>, response: Response<ModelAddPayment>
                ) {
                    try {
                        if (response.code() == 500) {
                            activity?.let { myToast(it, "Server Error") }
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            activity?.let { myToast(it, "Something went wrong") }
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            activity?.let { myToast(it, "Details Submitted") }
                            apiCallGetPaymentDet()
                            AppProgressBar.hideLoaderDialog()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        activity?.let { myToast(it, "Something went wrong") }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelAddPayment>, t: Throwable) {
                    count++
                    if (count <= 3) {
                        Log.e("count", count.toString())
                        apiCallAddPaymentDetail()
                    } else {
                        activity?.let { myToast(it, t.message.toString()) }
                        AppProgressBar.hideLoaderDialog()

                    }
                    AppProgressBar.hideLoaderDialog()
                }


            })
    }

}