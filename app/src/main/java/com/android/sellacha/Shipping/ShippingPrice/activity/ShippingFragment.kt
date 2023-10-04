package com.android.sellacha.Shipping.ShippingPrice.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.sellacha.Costomer.adapter.AdapterCustomer
import com.android.sellacha.Products.Coupons.MOdel.ModelCreateCoupon
import com.android.sellacha.Products.Coupons.activity.CouponsFragment
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.Shipping.Location.adapter.AdapterLoaction
import com.android.sellacha.Shipping.ShippingPrice.adapter.AdapterShipping
import com.android.sellacha.adapter.CustomerAdapter
import com.android.sellacha.api.model.inventoryDM
import com.android.sellacha.api.model.inventoryTypeDM
import com.android.sellacha.databinding.FragmentShippingBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShippingFragment : Fragment(), AdapterShipping.Delete {
    var binding: FragmentShippingBinding? = null
    var inventoryAdapter: CustomerAdapter? = null
    var inventoryTypeArr = ArrayList<inventoryTypeDM>()
    var inventoryArr = ArrayList<inventoryDM>()
    private lateinit var sessionManager: SessionManager
    private var mainData = ArrayList<DataX>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shipping, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShippingBinding.bind(view)
        sessionManager = SessionManager(requireContext())
        apiCallShipping()

        binding!!.searchTxt.addTextChangedListener { str ->
            setRecyclerViewAdapter(mainData.filter {
                it!!.name!!.contains(
                    str.toString(),
                    ignoreCase = true
                )
            } as java.util.ArrayList<DataX>)
        }

        binding!!.CreateMethod.setOnClickListener {
            edit = "1"
            Navigation.findNavController(binding!!.root).navigate(R.id.CreateShippingMethodFragment)
        }


    }

    private fun apiCallShipping() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getShipping(sessionManager.authToken)
            .enqueue(object : Callback<ModelCategory> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCategory>, response: Response<ModelCategory>
                ) {
                    try {
                        if (response.code() == 200) {
                            mainData = response.body()!!.data.posts.data
                            AppProgressBar.hideLoaderDialog()

                        }
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")

                        } else if (response.body()!!.data.posts.data.isEmpty()) {
                            binding!!.attributeRc.adapter =
                                activity?.let {
                                    AdapterShipping(
                                        it,
                                        response.body()!!.data.posts.data,
                                        this@ShippingFragment
                                    )
                                }
                            binding!!.attributeRc.adapter!!.notifyDataSetChanged()
                            myToast(requireActivity(), "No Data Found")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding!!.attributeRc.adapter =
                                activity?.let {
                                    AdapterShipping(
                                        it,
                                        response.body()!!.data.posts.data,
                                        this@ShippingFragment
                                    )
                                }
                            AppProgressBar.hideLoaderDialog()


                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallShipping()
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun setRecyclerViewAdapter(data: java.util.ArrayList<DataX>) {
        binding!!.attributeRc.apply {
            adapter =
                context?.let { AdapterShipping(requireContext(), data, this@ShippingFragment) }
        }
    }

    override fun delete(id: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure want to Delete?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener { sDialog ->
                sDialog.cancel()
                apiCallDelete(id)
            }
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
            }
            .show()
    }

    private fun apiCallDelete(id: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.deleteShipping(sessionManager.authToken, id, "delete")
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
                            apiCallShipping()
                            AppProgressBar.hideLoaderDialog()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelCreateCoupon>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallDelete(id)
                }

            })

    }


    override fun edit(id: String, titleNew: String, priceNew: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure want to edit?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener { sDialog ->
                sDialog.cancel()
                // apiCallDelete(id)
                edit = "2"
                title = titleNew
                price = priceNew
                idNew = id
                Navigation.findNavController(binding!!.root)
                    .navigate(R.id.CreateShippingMethodFragment)

            }
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
            }
            .show()
    }

    companion object {
        var edit = "1"
        var title = ""
        var price = ""
        var idNew = ""
    }
}