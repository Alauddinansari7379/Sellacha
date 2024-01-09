package com.android.sellacha.shopSetting.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.android.sellacha.Order.Model.ModelCoupon
import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.databinding.FragmentGeneralBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GeneralFragment : Fragment() {
    lateinit var binding: FragmentGeneralBinding
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null
    var curenctPosition = ""
    var shopType = ""
    var shopRecived = ""
    var currencyPositionList = ArrayList<ModelProductType>()
    var shopTypeList = ArrayList<ModelProductType>()
    var shopReceiveList = ArrayList<ModelProductType>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGeneralBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        binding!!.saveBtn.setOnClickListener {
            if (binding.StoreName.text!!.isEmpty()) {
                binding.StoreName.error = "Enter Store Name"
                binding.StoreName.requestFocus()
                return@setOnClickListener
            }
            if (binding.StoreDescription.text!!.isEmpty()) {
                binding.StoreDescription.error = "Enter Store Description"
                binding.StoreDescription.requestFocus()
                return@setOnClickListener
            }
            if (binding.StoreEmail.text!!.isEmpty()) {
                binding.StoreEmail.error = "Enter Store Email "
                binding.StoreEmail.requestFocus()
                return@setOnClickListener
            }
            if (binding.OrderID.text!!.isEmpty()) {
                binding.OrderID.error = "Enter Order ID"
                binding.OrderID.requestFocus()
                return@setOnClickListener
            }
            if (binding.CurrencyName.text!!.isEmpty()) {
                binding.CurrencyName.error = "Enter Currency Name"
                binding.CurrencyName.requestFocus()
                return@setOnClickListener
            }
            if (binding.CurrencyIcon.text!!.isEmpty()) {
                binding.CurrencyIcon.error = "Enter CurrencyIcon"
                binding.CurrencyIcon.requestFocus()
                return@setOnClickListener
            }
            if (binding.Tax.text!!.isEmpty()) {
                binding.Tax.error = "Enter Tax"
                binding.Tax.requestFocus()
                return@setOnClickListener
            }
            apiCallGeneral()

        }
        currencyPositionList.add(ModelProductType("Left", 0))
        currencyPositionList.add(ModelProductType("Right", 1))


        shopTypeList.add(ModelProductType("I will sale physical products", 0))
        shopTypeList.add(ModelProductType("I will sale digital products", 1))

        shopReceiveList.add(ModelProductType("I will Receive My Order Via Whatsapp", 0))
        shopReceiveList.add(ModelProductType("I will Receive My Order Via Email", 1))

        binding!!.CurrenctPosition.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            currencyPositionList
        )

        binding!!.ShopeType.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            shopTypeList
        )

        binding!!.ShopeReceive.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            shopReceiveList
        )

        binding!!.CurrenctPosition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (currencyPositionList.size > 0) {
                        curenctPosition = currencyPositionList[i].text

                        Log.e(ContentValues.TAG, "curenctPosition: $curenctPosition")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

        binding!!.ShopeType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (shopTypeList.size > 0) {
                        shopType = shopTypeList[i].value.toString()

                        Log.e(ContentValues.TAG, "curencshopTypetPosition: $shopType")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

        binding!!.ShopeReceive.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (shopReceiveList.size > 0) {
                        shopRecived = shopReceiveList[i].value.toString()

                        Log.e(ContentValues.TAG, "shopRecived: $shopRecived")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

    }


    private fun apiCallGeneral() {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.themeSettingsGeneral(
            sessionManager.authToken,
            "general",
            binding.StoreName.text.toString().trim(),
            binding.StoreDescription.text.toString().trim(),
            binding.StoreEmail.text.toString().trim(),
            binding.OrderID.text.toString().trim(),
            curenctPosition,
            binding.CurrencyName.text.toString().trim(),
            binding.CurrencyIcon.text.toString().trim(),
            shopRecived,
            shopType,
            binding.Tax.text.toString().trim(),


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
                    apiCallGeneral()

                }

            })
    }


}