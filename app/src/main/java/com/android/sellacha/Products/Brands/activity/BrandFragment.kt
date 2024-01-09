package com.android.sellacha.Products.Brands.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.sellacha.Products.Brands.adapter.AdapterBrand
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Products.categories.Model.ModelCreCatogoryJava
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentBrandBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrandFragment : Fragment(), AdapterBrand.Delete {
    var binding: FragmentBrandBinding? = null
    private lateinit var sessionManager: SessionManager
    private var mainData = java.util.ArrayList<DataX>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_brand, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBrandBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        binding!!.createBrand.setOnClickListener {
            edit="1"
            Navigation.findNavController(binding!!.root).navigate(R.id.CreateBrand)

        }
        apiCallBrand()

        binding!!.searchTxt.addTextChangedListener { str ->
            setRecyclerViewAdapter(mainData.filter {
                it!!.name!!.contains(
                    str.toString(),
                    ignoreCase = true
                )
            } as java.util.ArrayList<DataX>)
        }
    }

    private fun apiCallBrand() {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.getBrand(sessionManager.authToken)
            .enqueue(object : Callback<ModelCategory> {
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
                            binding!!.recyclerView.adapter =
                                activity?.let {
                                    AdapterBrand(
                                        it,
                                        response.body()!!.data.posts.data,
                                        this@BrandFragment
                                    )
                                }
                            binding!!.recyclerView.adapter!!.notifyDataSetChanged()
                            myToast(requireActivity(), "No Brand Found")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding!!.recyclerView.adapter =
                                activity?.let {
                                    AdapterBrand(
                                        it,
                                        response.body()!!.data.posts.data,
                                        this@BrandFragment
                                    )
                                }
                            AppProgressBar.hideLoaderDialog()


                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                    //myToast(requireActivity(), "Something went wrong")
                    apiCallBrand()
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun setRecyclerViewAdapter(data: java.util.ArrayList<DataX>) {
        binding!!.recyclerView.apply {
            adapter = context?.let { AdapterBrand(requireContext(), data, this@BrandFragment) }
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

        ApiClient.apiService.deleteBrand(sessionManager.authToken, id, "delete")
            .enqueue(object : Callback<ModelCreCatogoryJava> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreCatogoryJava>, response: Response<ModelCreCatogoryJava>
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
                            apiCallBrand()
                            AppProgressBar.hideLoaderDialog()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelCreCatogoryJava>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallDelete(id)
                }

            })

    }

    override fun edit(id: String, name: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure want to Edit?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener { sDialog ->
                sDialog.cancel()
                edit = "2"
                brandName = name
                idNew = id
                Navigation.findNavController(binding!!.root).navigate(R.id.CreateBrand)

            }
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
            }
            .show()
    }

    companion object {
        var edit = "1"
        var brandName = ""
        var idNew = ""
    }

}