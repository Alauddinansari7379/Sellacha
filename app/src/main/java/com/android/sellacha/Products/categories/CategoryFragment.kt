package com.android.sellacha.Products.categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Products.categories.adapter.AdapterAppointments
import com.android.sellacha.R
import com.android.sellacha.api.model.categoriesDM
import com.android.sellacha.databinding.FragmentCategoryBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var sessionManager: SessionManager
    var categoriesArr = ArrayList<categoriesDM>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }
    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)
        sessionManager = SessionManager(requireContext())
        apiCallCategory()

    }
    private fun apiCallCategory() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getCategory("Bearer 5|MuaOrW4WznVlYnCgjGRcJlopUqd2RJztSKcjorLG","category")
            .enqueue(object : Callback<ModelCategory> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCategory>, response: Response<ModelCategory>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding.rvCancled.adapter =
                            activity?.let { AdapterAppointments(it, response.body()!!) }
                        binding.rvCancled.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Appointment Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding.rvCancled.adapter =
                            activity?.let { AdapterAppointments(it, response.body()!!) }
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