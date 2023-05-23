package com.android.sellacha.Products.Brands

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.Products.Brands.adapter.AdapterBrand
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentBrandBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrandFragment : Fragment() {
    var binding: FragmentBrandBinding? = null
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_brand, container, false)
    }
    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBrandBinding.bind(view)
        sessionManager = SessionManager(requireContext())
        apiCallBrand()
    }
    private fun apiCallBrand() {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.getBrand(sessionManager.authToken)
            .enqueue(object : Callback<ModelCategory> {
                override fun onResponse(
                    call: Call<ModelCategory>, response: Response<ModelCategory>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding!!.recyclerView.adapter =
                            activity?.let { AdapterBrand(it, response.body()!!) }
                        binding!!.recyclerView.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Brand Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.recyclerView.adapter =
                            activity?.let { AdapterBrand(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}