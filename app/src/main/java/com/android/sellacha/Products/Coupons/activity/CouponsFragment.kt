package com.android.sellacha.Products.Coupons.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.Coupons.MOdel.ModelCoupons
import com.android.sellacha.Products.Coupons.adapter.AdapterCoupons
import com.android.sellacha.R
import com.android.sellacha.api.model.reviewRatingDM
import com.android.sellacha.databinding.FragmentCouponsBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import kotlinx.coroutines.handleCoroutineException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CouponsFragment : Fragment() {
    var binding: FragmentCouponsBinding? = null
    var reviewRatingArr = ArrayList<reviewRatingDM>()
    private lateinit var sessionManager: SessionManager
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
        sessionManager=SessionManager(requireContext())


        apiCallCoupons()


    }

    private fun apiCallCoupons() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getCoupon(sessionManager.authToken)
            .enqueue(object : Callback<ModelCoupons> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupons>, response: Response<ModelCoupons>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding!!.reviewRatingRc.adapter =
                            activity?.let { AdapterCoupons(it, response.body()!!) }
                        binding!!.reviewRatingRc.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Coupons Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.reviewRatingRc.adapter =
                            activity?.let { AdapterCoupons(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCoupons>, t: Throwable) {
                   // myToast(requireActivity(),t.message.toString())
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}