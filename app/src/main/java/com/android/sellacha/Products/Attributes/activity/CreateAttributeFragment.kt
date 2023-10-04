package com.android.sellacha.Products.Attributes.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.Order.Model.ModelCoupon
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.Coupons.MOdel.ModelCreateCoupon
import com.android.sellacha.Products.Coupons.activity.CouponsFragment
import com.android.sellacha.Products.categories.CategoryFragment
import com.android.sellacha.Products.categories.Model.ModelFeatured
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentCreateAttributeBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAttributeFragment : Fragment() {
    private lateinit var binding: FragmentCreateAttributeBinding
    var featuredList = ArrayList<ModelFeatured>()
    var featured = ""
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_attribute, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateAttributeBinding.bind(view)

        if (AttributeFragment.edit =="2"){
            binding!!.btnSave.text="Update"
            binding!!.editTextTextPersonName.setText(AttributeFragment.presonName)
         }

        sessionManager = SessionManager(requireContext())
        featuredList.add(ModelFeatured("Yes", "1"))
        featuredList.add(ModelFeatured("No", "0"))
        binding.spinnerFeature.adapter = ArrayAdapter<ModelFeatured>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            featuredList
        )

        Log.e("sessionManager.authToken,", sessionManager.authToken.toString())

        binding.btnSave.setOnClickListener {
            if (binding.editTextTextPersonName.text.isEmpty()) {
                binding.editTextTextPersonName.error = "Title Required"
                binding.editTextTextPersonName.requestFocus()
                return@setOnClickListener
            } else {
                var title = binding.editTextTextPersonName.text.toString().trim()
                if (AttributeFragment.edit =="2"){
                    apiCallEdit()
                }else{
                    apiCallCreateAttribute(title)

                }
            }
        }

        binding.spinnerFeature.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    if (featuredList.size > 0) {
                        featured = featuredList[i].value
                        Log.e(ContentValues.TAG, "parentCate: $featuredList")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
    }

    private fun apiCallCreateAttribute(title: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.createAttribute(
            sessionManager.authToken,
            title,
            featured
        )
            .enqueue(object : Callback<ModelCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupon>, response: Response<ModelCoupon>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.code() == 401) {
                        myToast(requireActivity(), "Maximum Attribute limit exceeded")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(requireActivity(), response.body()!!.data)
                        Navigation.findNavController(binding!!.root).navigate(R.id.attributeFragment)

                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
    private fun apiCallEdit() {

        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.editAttribute(sessionManager.authToken,
            binding!!.editTextTextPersonName.text.toString().trim(),
            featured,
             AttributeFragment.idNew
        )
            .enqueue(object : Callback<ModelCreateCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateCoupon>, response: Response<ModelCreateCoupon>
                ) {
                    try {

                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            myToast(requireActivity(), "Something went wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 401) {
                            myToast(requireActivity(), "Something went wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            myToast(requireActivity(), response.body()!!.data)
                            AttributeFragment.edit ="1"
                            Navigation.findNavController(binding!!.root).navigate(R.id.attributeFragment)
                            AppProgressBar.hideLoaderDialog()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelCreateCoupon>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Try Again")
                    AppProgressBar.hideLoaderDialog()
                    apiCallEdit()
                }

            })

    }
    override fun onDestroy() {
        super.onDestroy()
        AttributeFragment.edit ="1"
    }
}