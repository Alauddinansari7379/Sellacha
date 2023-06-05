package com.android.sellacha.Shipping

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.Shipping.Location.adapter.AdapterLoaction
import com.android.sellacha.databinding.FragmentLocationBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationFragment : Fragment() {
    lateinit var binding: FragmentLocationBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationBinding.bind(view)
        sessionManager = SessionManager(requireContext())
        apiCallLocation()



        binding!!.CreateLocation.setOnClickListener { view ->
            Navigation.findNavController(binding!!.root).navigate(R.id.CreateLocationFragment)
        }


    }

    private fun apiCallLocation() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getLocation(sessionManager.authToken)
            .enqueue(object : Callback<ModelCategory> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCategory>, response: Response<ModelCategory>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding!!.locationList.adapter =
                            activity?.let { AdapterLoaction(it, response.body()!!) }
                        binding!!.locationList.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Location Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.locationList.adapter =
                            activity?.let { AdapterLoaction(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                   // myToast(requireActivity(), "Something went wrong")
                    apiCallLocation()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
}
