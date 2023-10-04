package com.android.sellacha.OfferAndAds.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.OfferAndAds.adapter.AdapterBumpAd
import com.android.sellacha.OfferAndAds.model.ModelBumpAd
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentBannerAdsBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BannerAdsFragment : Fragment(),AdapterBumpAd.Delete {
    var binding: FragmentBannerAdsBinding? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_banner_ads, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBannerAdsBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        binding!!.btnCreateNew.setOnClickListener {
            Navigation.findNavController(binding!!.root).navigate(R.id.CreateBaanerAd)
        }
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("FetchContact89", "fetchContacts: coroutine start")
            apiCallBannerAd()

        }
    }

    private fun apiCallBannerAd() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.bannerAds(sessionManager.authToken)
            .enqueue(object : Callback<ModelBumpAd> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelBumpAd>, response: Response<ModelBumpAd>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.data.isEmpty()) {
                        binding!!.recyclerView.adapter =
                            activity?.let { AdapterBumpAd(it, response.body()!!,this@BannerAdsFragment) }
                        binding!!.recyclerView.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Ad Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.recyclerView.adapter =
                            activity?.let { AdapterBumpAd(it, response.body()!!,this@BannerAdsFragment) }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelBumpAd>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallBannerAd()

                   // AppProgressBar.hideLoaderDialog()


                }

            })
    }

    override fun delete(id: String) {
     }


}