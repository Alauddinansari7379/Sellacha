package com.android.sellacha.setting.Domain

import android.annotation.SuppressLint
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentDomainSettingBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.setting.Domain.model.ModelDomain
import com.android.sellacha.setting.Domain.model.ModelUpdateDomain
import com.android.sellacha.sharedpreferences.SessionManager
import com.android.sellacha.utils.AppProgressBar
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DomainSettingFragment : Fragment() {
    var binding: FragmentDomainSettingBinding? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null
    var url = "#"
    var count = 0
    var count1= 0
    var count2= 0
    var dialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_domain_setting, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDomainSettingBinding.bind(view)

        sessionManager = SessionManager(requireContext())

        binding!!.btnchoosefile.setOnClickListener {
        }

        binding!!.saveBtn.setOnClickListener {
            apiCallDomainDetailDialog()
        }
        apiCallDomainDetail()

    }

    private fun apiCallDomainDetail() {
        AppProgressBar.showLoaderDialog(activity)


        ApiClient.apiService.domainDetails(
            sessionManager.authToken.toString()
        )
            .enqueue(object : Callback<ModelDomain> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelDomain>, response: Response<ModelDomain>
                ) {
                    try {
                        if (response.code() == 500) {
                            activity?.let { myToast(it, "Server Error") }
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            activity?.let { myToast(it, "Something went wrong") }
                            AppProgressBar.hideLoaderDialog()

                        } else {
                             binding!!.txtName.setText( "https://storefront.sellacha.com")
                            binding!!.textView21.text=response.body()!!.data.request.domain
                            AppProgressBar.hideLoaderDialog()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        activity?.let { myToast(it, "Something went wrong") }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelDomain>, t: Throwable) {
                    count++
                    if (count <= 3) {
                        Log.e("count", count.toString())
                        apiCallDomainDetail()
                    } else {
                        activity?.let { myToast(it, t.message.toString()) }
                        AppProgressBar.hideLoaderDialog()

                    }
                    AppProgressBar.hideLoaderDialog()
                }


            })
    }
    private fun apiCallDomainDetailDialog() {
        AppProgressBar.showLoaderDialog(activity)


        ApiClient.apiService.domainDetails(
            sessionManager.authToken.toString()
        )
            .enqueue(object : Callback<ModelDomain> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelDomain>, response: Response<ModelDomain>
                ) {
                    try {
                        if (response.code() == 500) {
                            activity?.let { myToast(it, "Server Error") }
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            activity?.let { myToast(it, "Something went wrong") }
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            val view = layoutInflater.inflate(R.layout.dialog_custom_domain, null)
                            dialog = activity?.let { it1 -> Dialog(it1) }
                            val imgClose = view!!.findViewById<ImageView>(R.id.imgCloseDil)
                            val edtCustomDomain = view.findViewById<EditText>(R.id.edtCustomDomain)
                            val tvConfigureInstruction = view.findViewById<TextView>(R.id.tvConfigureInstruction)
                            val tvSupportInstruction = view.findViewById<TextView>(R.id.tvSupportInstruction)
                            val tvCancel = view.findViewById<TextView>(R.id.tvCancel)
                            val btnConnect = view.findViewById<TextView>(R.id.btnConnect)

                            edtCustomDomain.setText(response.body()!!.data.request.domain)
                            tvConfigureInstruction.text=response.body()!!.data.dns.dns_configure_instruction
                            tvSupportInstruction.text=response.body()!!.data.dns.support_instruction

                            dialog = activity?.let { it1 -> Dialog(it1) }
                            if (view.parent != null) {
                                (view.parent as ViewGroup).removeView(view) // <- fix
                            }
                            dialog!!.setContentView(view)
                            dialog?.setCancelable(true)

                            dialog?.show()

                            imgClose.setOnClickListener {
                                dialog?.dismiss()
                            }
                            tvCancel.setOnClickListener {
                                dialog?.dismiss()
                            }

                            btnConnect.setOnClickListener {
                                dialog?.dismiss()
                                apiCallDomainUpdate(edtCustomDomain.text.toString())

                            }
                            AppProgressBar.hideLoaderDialog()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        activity?.let { myToast(it, "Something went wrong") }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelDomain>, t: Throwable) {
                    count1++
                    if (count1 <= 3) {
                        Log.e("count", count1.toString())
                        apiCallDomainDetailDialog()
                    } else {
                        activity?.let { myToast(it, t.message.toString()) }
                        AppProgressBar.hideLoaderDialog()

                    }
                    AppProgressBar.hideLoaderDialog()
                }


            })
    }
    private fun apiCallDomainUpdate(domain:String) {
        AppProgressBar.showLoaderDialog(activity)


        ApiClient.apiService.domainUpdate(
            sessionManager.authToken.toString(),domain
        )
            .enqueue(object : Callback<ModelUpdateDomain> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelUpdateDomain>, response: Response<ModelUpdateDomain>
                ) {
                    try {
                        if (response.code() == 500) {
                            activity?.let { myToast(it, "Server Error") }
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            activity?.let { myToast(it, "Something went wrong") }
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            //Model need to change
                            activity?.let { myToast(it,response.body()!!.data.errors.domain) }
                            AppProgressBar.hideLoaderDialog()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        activity?.let { myToast(it, "Something went wrong") }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelUpdateDomain>, t: Throwable) {
                    count2++
                    if (count2 <= 3) {
                        Log.e("count", count2.toString())
                        apiCallDomainUpdate(domain)
                    } else {
                        activity?.let { myToast(it, t.message.toString()) }
                        AppProgressBar.hideLoaderDialog()

                    }
                    AppProgressBar.hideLoaderDialog()
                }


            })
    }


}