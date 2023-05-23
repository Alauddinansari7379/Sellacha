package com.android.sellacha.Products.Attributes.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.Attributes.adapter.AdapterAttribute
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentAttributeBinding
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

class AttributeFragment : Fragment() {
    var binding: FragmentAttributeBinding? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_attribute, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAttributeBinding.bind(view)
        sessionManager = SessionManager(requireContext())
        apiCallAttribute()
        binding!!.createAttribute.setOnClickListener { view ->
            Navigation.findNavController(binding!!.root).navigate(R.id.createAttributeFragment)
        }


        CoroutineScope(Dispatchers.IO).launch {
            Log.d("FetchContact89", "fetchContacts: coroutine start")


        }
    }

    private fun apiCallAttribute() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getAttribute(sessionManager.authToken)
            .enqueue(object : Callback<ModelAttributes> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelAttributes>, response: Response<ModelAttributes>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.body()!!.data.posts.isEmpty()) {
                        binding!!.recyclerView.adapter =
                            activity?.let { AdapterAttribute(it, response.body()!!) }
                        binding!!.recyclerView.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Data Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.recyclerView.adapter =
                            activity?.let { AdapterAttribute(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelAttributes>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}