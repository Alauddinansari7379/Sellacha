package com.android.sellacha.Products.Coupons.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.sellacha.Products.Coupons.MOdel.DataX
import com.android.sellacha.Products.Coupons.MOdel.ModelCoupons
import com.android.sellacha.Products.Coupons.MOdel.ModelCreateCoupon
import com.android.sellacha.Products.Coupons.adapter.AdapterCoupons
 import com.android.sellacha.R
import com.android.sellacha.api.model.reviewRatingDM
import com.android.sellacha.databinding.FragmentCouponsBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CouponsFragment : Fragment(),AdapterCoupons.Delete {
    var binding: FragmentCouponsBinding? = null
    var reviewRatingArr = ArrayList<reviewRatingDM>()
    private lateinit var sessionManager: SessionManager
    private var mainData = ArrayList<DataX>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coupons, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCouponsBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        binding!!.CreateCoupons.setOnClickListener {
            edit="1"
            Navigation.findNavController(binding!!.root).navigate(R.id.CreateCouponFragment)

        }


        apiCallCoupons()
        binding!!.searchTxt.addTextChangedListener { str ->
            setRecyclerViewAdapter(mainData.filter {
                it!!.name!!.contains(
                    str.toString(),
                    ignoreCase = true
                )
            } as java.util.ArrayList<DataX>)
        }

    }

    private fun apiCallCoupons() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getCoupon(sessionManager.authToken)
            .enqueue(object : Callback<ModelCoupons> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupons>, response: Response<ModelCoupons>
                ) {
                    try {
                        if (response.code() == 200) {
                            mainData = response.body()!!.data.posts.data
                            AppProgressBar.hideLoaderDialog()

                        }
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.body()!!.data.posts.data.isEmpty()) {
                            binding!!.reviewRatingRc.adapter =
                                activity?.let {
                                    AdapterCoupons(
                                        it,
                                        response.body()!!.data.posts.data,
                                        this@CouponsFragment
                                    )
                                }
                            binding!!.reviewRatingRc.adapter!!.notifyDataSetChanged()
                            myToast(requireActivity(), "No Coupons Found")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding!!.reviewRatingRc.adapter =
                                activity?.let {
                                    AdapterCoupons(
                                        it,
                                        response.body()!!.data.posts.data,
                                        this@CouponsFragment
                                    )
                                }
                            AppProgressBar.hideLoaderDialog()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelCoupons>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallCoupons()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun setRecyclerViewAdapter(data: java.util.ArrayList<DataX>) {
        binding!!.reviewRatingRc.apply {
            adapter = context?.let {
                AdapterCoupons(requireContext(), data, this@CouponsFragment)
            }
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

    override fun edit( code: String, exDateNew: String,Id: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure want to edit?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener { sDialog ->
                sDialog.cancel()
               // apiCallDelete(id)
                edit="2"
                couponCode=code
                exDate= exDateNew
                idNew= Id
                Navigation.findNavController(binding!!.root).navigate(R.id.CreateCouponFragment)

            }
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
            }
            .show()
    }

    private fun apiCallDelete(id: String) {

        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.deleteCoupon(sessionManager.authToken, id, "delete")
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
                            apiCallCoupons()
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
                    delete(id)
                }

            })

    }
    companion object{
        var edit="1"
        var couponCode=""
        var exDate=""
        var idNew=""
    }
}