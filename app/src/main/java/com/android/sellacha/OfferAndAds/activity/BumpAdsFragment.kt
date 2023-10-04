package com.android.sellacha.OfferAndAds.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.sellacha.OfferAndAds.adapter.AdapterBumpAd
import com.android.sellacha.OfferAndAds.model.ModelBumpAd
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentBumpAdsBinding
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

class BumpAdsFragment : Fragment(), AdapterBumpAd.Delete {
    var binding: FragmentBumpAdsBinding? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bump_ads, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBumpAdsBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        binding!!.btnCreateNew.setOnClickListener {
            Navigation.findNavController(binding!!.root).navigate(R.id.CreateBumpAd)
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("FetchContact89", "fetchContacts: coroutine start")
            apiCallBumpAd()

        }
    }

    private fun apiCallBumpAd() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.bumpAds(sessionManager.authToken)
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
                            activity?.let { AdapterBumpAd(it, response.body()!!,this@BumpAdsFragment) }
                        binding!!.recyclerView.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Ad Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.recyclerView.adapter =
                            activity?.let { AdapterBumpAd(it, response.body()!!,this@BumpAdsFragment) }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelBumpAd>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallBumpAd()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun apiCallDelete(id: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.deleteBumpAds(sessionManager.authToken,id)
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
                            activity?.let { AdapterBumpAd(it, response.body()!!,this@BumpAdsFragment) }
                        binding!!.recyclerView.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Ad Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.recyclerView.adapter =
                            activity?.let { AdapterBumpAd(it, response.body()!!,this@BumpAdsFragment) }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelBumpAd>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallBumpAd()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    override fun delete(id: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure want to edit?")
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


}