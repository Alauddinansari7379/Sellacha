package com.android.sellacha.Shipping.Location.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.android.sellacha.R
import com.android.sellacha.Shipping.Location.ModelCreateLocation
import com.android.sellacha.api.model.categoriesDM
import com.android.sellacha.databinding.FragmentCreateLocationBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateLocationFragment : Fragment() {
    private lateinit var binding: FragmentCreateLocationBinding
    private lateinit var sessionManager: SessionManager
    var navController: NavController? = null
    val handler = Handler(Looper.getMainLooper())

    var categoriesArr = ArrayList<categoriesDM>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_location, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateLocationBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        binding.btnSave.setOnClickListener {
            if (binding.txtTitle.text.toString().isEmpty()){
                binding.txtTitle.error="Enter Location"
                binding.txtTitle.requestFocus()
                return@setOnClickListener
            }
            val location= binding.txtTitle.text.toString().trim()
            apiCallCategory(location)
        }


    }

    private fun apiCallCategory(location: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.createLocation(sessionManager.authToken,location)
            .enqueue(object : Callback<ModelCreateLocation> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateLocation>, response: Response<ModelCreateLocation>
                )
                {
                    try {
                        if (response.code() == 500) {
                            myToast(requireContext() as Activity, "Server Error")
                            AppProgressBar.hideLoaderDialog()
                        }
                        if (response.body()!!.success) {
                            myToast(requireActivity(), response.body()!!.data)
                            binding.txtTitle.text.clear()
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 401) {
                            myToast(
                                requireContext() as Activity,
                                "Maximum Location Exceeded Please Update Your Plan"
                            )
                            AppProgressBar.hideLoaderDialog()
                        } else {
                            myToast(requireActivity(), response.body()!!.data)
                            //  myToast(requireActivity(), response.message())
                            AppProgressBar.hideLoaderDialog()

                        }
                        // apiCallGetPrePending1()

                        //  binding.prog[
                    //  e.ressBar.progress = 100
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                        myToast(requireActivity(), "Something went wrong")
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCreateLocation>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}