package com.android.sellacha.Products.categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.android.sellacha.Products.categories.Model.ModeCategoryJava
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Products.categories.adapter.AdapterCategory
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
    var navController: NavController? = null
    val handler = Handler(Looper.getMainLooper())

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


        binding.createCategories.setOnClickListener {
                view -> findNavController(binding.root).navigate(R.id.createCategoryFragment) }
    }
//    protected fun getNavOptions(): NavOptions? {
//        return Builder()
//            .setEnterAnim(R.anim.wait)
//            .setExitAnim(R.anim.slide_nav)
//            .setPopEnterAnim(R.anim.slide_right)
//            .setPopExitAnim(R.anim.slide_nav)
//            .build()
//    }


    private fun apiCallCategory() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getCategory(sessionManager.authToken,"category")
            .enqueue(object : Callback<ModelCategory> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCategory>, response: Response<ModelCategory>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    }

                    else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding.recyclerView.adapter =
                            activity?.let { AdapterCategory(it, response.body()!!) }
                        binding.recyclerView.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Category Found")
                        AppProgressBar.hideLoaderDialog()

                    }
                    else {
                        binding.recyclerView.adapter =
                            activity?.let { AdapterCategory(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                    //myToast(requireActivity(), "Something went wrong")
                    apiCallCategory()
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}