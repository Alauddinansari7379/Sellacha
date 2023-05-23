package com.android.sellacha.Costomer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sellacha.Costomer.adapter.AdapterCustomer
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.Shipping.Location.adapter.AdapterLoaction
import com.android.sellacha.adapter.CustomerAdapter
import com.android.sellacha.api.model.inventoryDM
import com.android.sellacha.api.model.inventoryTypeDM
import com.android.sellacha.databinding.FragmentCustomerBinding
import com.android.sellacha.databinding.FragmentLocationBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerFragment : Fragment() {
    var binding: FragmentCustomerBinding? = null
    var inventoryAdapter: CustomerAdapter? = null
    var inventoryTypeArr = ArrayList<inventoryTypeDM>()
    var inventoryArr = ArrayList<inventoryDM>()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_customer, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomerBinding.bind(view)
        sessionManager = SessionManager(requireContext())
        apiCallCustomer()


        binding!!.createAttribute.setOnClickListener { view ->
            Navigation.findNavController(binding!!.root).navigate(R.id.createCustomerFragment)
        }


    }

    private fun apiCallCustomer() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getCustomer(sessionManager.authToken)
            .enqueue(object : Callback<ModelCategory> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCategory>, response: Response<ModelCategory>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding!!.attributeRc.adapter =
                            activity?.let { AdapterCustomer(it, response.body()!!) }
                        binding!!.attributeRc.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Location Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.attributeRc.adapter =
                            activity?.let { AdapterCustomer(it, response.body()!!) }
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