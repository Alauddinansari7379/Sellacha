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
import com.android.sellacha.Products.Inventory.Model.DataInverntor
import com.android.sellacha.Products.Inventory.adapter.AdapterInventory
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
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.sellacha.Products.Attributes.activity.AttributeFragment
import com.android.sellacha.Products.Coupons.MOdel.ModelCreateCoupon
import com.android.sellacha.Products.categories.Model.DataX


class CategoryFragment : Fragment() ,AdapterCategory.Delete{
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var sessionManager: SessionManager
    var navController: NavController? = null
    val handler = Handler(Looper.getMainLooper())
    private var mainData = java.util.ArrayList<DataX>()
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

        binding!!.searchTxt.addTextChangedListener { str ->
            setRecyclerViewAdapter(mainData.filter {
                it!!.name!!.contains(
                    str.toString(),
                    ignoreCase = true
                )
            } as java.util.ArrayList<DataX>)
        }


            apiCallCategory()

        binding.createCategories.setOnClickListener {
            CategoryFragment.edit ="1"
            findNavController(binding.root).navigate(R.id.createCategoryFragment)
        }
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
                    try {
                        if (response.code() == 200) {
                            mainData = response.body()!!.data.posts.data
                            AppProgressBar.hideLoaderDialog()

                        }
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")

                        } else if (response.body()!!.data.posts.data.isEmpty()) {
                            binding.recyclerView.adapter =
                                activity?.let { AdapterCategory(it, response.body()!!.data.posts.data,this@CategoryFragment) }
                            binding.recyclerView.adapter!!.notifyDataSetChanged()
                            myToast(requireActivity(), "No Category Found")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding.recyclerView.adapter =
                                activity?.let { AdapterCategory(it,response.body()!!.data.posts.data,this@CategoryFragment) }
                            AppProgressBar.hideLoaderDialog()


                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                    //myToast(requireActivity(), "Something went wrong")
                    apiCallCategory()
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
    private fun setRecyclerViewAdapter(data: java.util.ArrayList<DataX>) {
        binding!!.recyclerView.apply {
            adapter = context?.let { AdapterCategory(requireContext(), data,this@CategoryFragment) }
        }
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
    private fun apiCallDelete(id: String) {

        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.deleteAttribute(sessionManager.authToken, id, "delete")
            .enqueue(object : Callback<ModelCreateCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateCoupon>, response: Response<ModelCreateCoupon>
                ) {
                    try {

                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            myToast(requireActivity(), "Something went wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 401) {
                            myToast(requireActivity(), "Something went wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            myToast(requireActivity(), response.body()!!.data)
                            apiCallCategory()
                            AppProgressBar.hideLoaderDialog()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelCreateCoupon>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallDelete(id)
                }

            })

    }


    override fun edit(id: String, name: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure want to edit?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener { sDialog ->
                sDialog.cancel()
                // apiCallDelete(id)
                cateogoryName =name
                idNew =id
                edit ="2"
                findNavController(binding!!.root).navigate(R.id.createCategoryFragment)

            }
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
            }
            .show()
    }
    companion object {
         var edit="1"
        var cateogoryName=""
        var idNew=""
    }


}