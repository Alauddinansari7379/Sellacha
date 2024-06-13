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
import com.android.sellacha.databinding.FragmentInstamojoBinding
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

class InstamojoFragment : Fragment() {
    var binding: FragmentInstamojoBinding? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null
    var url = "#"
    var count = 0
    var statues = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instamojo, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInstamojoBinding.bind(view)

        sessionManager = SessionManager(requireContext())

        apiCallGetPaymentDet()
        with(binding) {
            this?.saveBtn!!.setOnClickListener {
                if (edtTitle.text.toString().isEmpty()) {
                    edtTitle.error = "Enter Display Name"
                    edtTitle.requestFocus()
                    return@setOnClickListener
                }

                if (edtPrivateKey.text.toString().isEmpty()) {
                    edtPrivateKey.error = "Enter PrivateKey"
                    edtPrivateKey.requestFocus()
                    return@setOnClickListener
                }
                if (edtAuthKey.text.toString().isEmpty()) {
                    edtAuthKey.error = "Enter AuthKey"
                    edtAuthKey.requestFocus()
                    return@setOnClickListener
                }

                if (edrPurpose.text.toString().isEmpty()) {
                    edrPurpose.error = "Enter Purpose"
                    edrPurpose.requestFocus()
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

    }

    private fun apiCallGetPaymentDet() {
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
                            if (response.body()!!.data.posts.isEmpty()) {
                                activity?.let { myToast(it, "Not found") }
                                AppProgressBar.hideLoaderDialog()

                            } else {
                                for (i in response.body()!!.data.posts) {
                                    var title = ""
                                    var env = ""
                                    var privateApiKey = ""
                                    var privateAuthToken = ""
                                    var purpose = ""
                                    var status = 0
                                    if (i.active_getway.id == 31) {
                                        val jsonString = i.active_getway.content
                                        val jsonObject = JSONObject(jsonString)
                                        title = jsonObject.getString("title")
                                        env = jsonObject.getString("env")
                                        privateApiKey = jsonObject.getString("private_api_key")
                                        privateAuthToken =
                                            jsonObject.getString("private_auth_token")
                                        purpose = jsonObject.getString("purpose")
                                        status = i.active_getway.status
                                        binding!!.edtTitle.setText(title)
                                        binding!!.edtPrivateKey.setText(privateApiKey)
                                        binding!!.edtAuthKey.setText(privateAuthToken)
                                        binding!!.edrPurpose.setText(purpose)
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
                        // activity?.let { myToast(it, "Something went wrong") }
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
            "31",
            "",
            binding!!.edtTitle.text.toString(),
            "",
            binding!!.edtPrivateKey.text.toString(),
            binding!!.edtAuthKey.text.toString(),
            binding!!.edrPurpose.text.toString(),
            "",
            "",
            "",
            "",
            "",
            "",
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