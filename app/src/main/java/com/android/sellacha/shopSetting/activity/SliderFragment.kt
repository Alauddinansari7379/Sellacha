package com.android.sellacha.shopSetting.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentSliderBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.shopSetting.adapter.AdapterSlider
import com.android.sellacha.shopSetting.model.ModelGetSlider
import com.android.sellacha.shopSetting.model.ModelSlider
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SliderFragment : Fragment(), AdapterSlider.Delete {
    var binding: FragmentSliderBinding? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSliderBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        binding!!.btnCreateNew.setOnClickListener {
            Navigation.findNavController(binding!!.root).navigate(R.id.SliderCreateFragment)
        }
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("FetchContact89", "fetchContacts: coroutine start")
            apiCallSlider()

        }
    }

    private fun apiCallSlider() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getSlider(sessionManager.authToken)
            .enqueue(object : Callback<ModelGetSlider> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelGetSlider>, response: Response<ModelGetSlider>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.posts.isEmpty()) {
                        binding!!.recyclerView.adapter =
                            activity?.let {
                                AdapterSlider(
                                    it,
                                    response.body()!!,
                                    this@SliderFragment
                                )
                            }
                        binding!!.recyclerView.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Slider Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.recyclerView.adapter =
                            activity?.let {
                                AdapterSlider(
                                    it,
                                    response.body()!!,
                                    this@SliderFragment
                                )
                            }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelGetSlider>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallSlider()

                    // AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun apiCallDelete(id: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.deleteSlider(sessionManager.authToken, id)
            .enqueue(object : Callback<ModelSlider> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelSlider>, response: Response<ModelSlider>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.code() == 404) {
                        myToast(requireActivity(), "Something went wrong")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(requireActivity(), response.body()!!.data)
                        apiCallSlider()
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelSlider>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallSlider()

                    AppProgressBar.hideLoaderDialog()


                }

            })
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


}