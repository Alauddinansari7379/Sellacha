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
import com.android.sellacha.databinding.FragmentWhatsAppApiBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.marketingTools.model.ModelGoolgeAna
import com.android.sellacha.marketingTools.model.ModelWhatsaap
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WhatsAppApiFragment : Fragment() {
    lateinit var binding: FragmentWhatsAppApiBinding
    lateinit var sessionManager: SessionManager
    var statuseList = ArrayList<ModelProductType>()
    var status = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_whats_app_api, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWhatsAppApiBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        apiCallGetWhatsapp()
        binding.saveBtn.setOnClickListener {
            if (binding.txtWhatsappNo.text.isEmpty()) {
                binding.txtWhatsappNo.error = "Enter Whatsapp No"
                binding.txtWhatsappNo.requestFocus()
                return@setOnClickListener
            }
            if (binding.txtPretextForProd.text.isEmpty()) {
                binding.txtPretextForProd.error = "Enter Pretext For Product"
                binding.txtPretextForProd.requestFocus()
                return@setOnClickListener
            }
            if (binding.txtOtherPagePre.text.isEmpty()) {
                binding.txtOtherPagePre.error = "Enter Other Page Pretext"
                binding.txtOtherPagePre.requestFocus()
                return@setOnClickListener
            }
            apiCallWhatsapp()
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


    private fun apiCallWhatsapp() {

        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.marketingWhatsapp(
            sessionManager.authToken,
            "whatsapp",
            binding.txtWhatsappNo.text.toString().trim(),
            binding.txtPretextForProd.text.toString().trim(),
            binding.txtOtherPagePre.text.toString().trim(),
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
                            binding.txtWhatsappNo.text.clear()
                            binding.txtPretextForProd.text.clear()
                            binding.txtOtherPagePre.text.clear()
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
                    apiCallWhatsapp()

                }

            })
    }
    private fun apiCallGetWhatsapp() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.marketingGetWhatsapp(
            sessionManager.authToken,
            "whatsapp",
        )
            .enqueue(object : Callback<ModelWhatsaap> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelWhatsaap>, response: Response<ModelWhatsaap>
                ) {
                    try {
                        if (response.code() == 404) {
                            myToast(requireActivity(), "Something went Wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding.txtWhatsappNo.setText(response.body()!!.data.json.phone_number)
                            binding.txtPretextForProd.setText(response.body()!!.data.json.shop_page_pretext)
                            binding.txtOtherPagePre.setText(response.body()!!.data.json.other_page_pretext)
                            AppProgressBar.hideLoaderDialog()

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        myToast(requireActivity(), "Something went Wrong")
                        AppProgressBar.hideLoaderDialog()

                    }
                }


                override fun onFailure(call: Call<ModelWhatsaap>, t: Throwable) {
                    //  myToast(this@HomeFragment, t.message.toString())
                    apiCallGetWhatsapp()

                }

            })
    }

}
