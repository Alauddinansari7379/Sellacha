package com.android.sellacha.Costomer.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.sellacha.Costomer.adapter.AdapterCustomer
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Products.categories.Model.ModelCreCatogoryJava
import com.android.sellacha.R
import com.android.sellacha.adapter.CustomerAdapter
import com.android.sellacha.api.model.inventoryDM
import com.android.sellacha.api.model.inventoryTypeDM
import com.android.sellacha.databinding.FragmentCustomerBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerFragment : Fragment(),AdapterCustomer.Delete {
    var binding: FragmentCustomerBinding? = null
    var inventoryAdapter: CustomerAdapter? = null
    var inventoryTypeArr = ArrayList<inventoryTypeDM>()
    var inventoryArr = ArrayList<inventoryDM>()
    private lateinit var sessionManager: SessionManager
    private var mainData = ArrayList<DataX>()

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

        binding!!.searchTxt.addTextChangedListener { str ->
            setRecyclerViewAdapter(mainData.filter {
                it!!.name!!.contains(
                    str.toString(),
                    ignoreCase = true
                )
            } as java.util.ArrayList<DataX>)
        }

        binding!!.createAttribute.setOnClickListener {
            edit="1"
            findNavController(binding!!.root).navigate(R.id.createCustomerFragment)
        }

        binding!!.imgRefresh.setOnClickListener { view ->
            binding!!.edtSearch.text.clear()
            apiCallCustomer()
        }

//        binding!!.imgSearch.setOnClickListener { view ->
//            if (binding!!.edtSearch.text.isEmpty()){
//                binding!!.edtSearch.error="Enter Customer Name"
//                binding!!.edtSearch.requestFocus()
//                return@setOnClickListener
//            }
//
//            apiCallSearchCustomer(binding!!.edtSearch.text.toString())
//         }


    }

    private fun apiCallCustomer() {
        AppProgressBar.showLoaderDialog(activity)

        ApiClient.apiService.getCustomer(sessionManager.authToken)
            .enqueue(object : Callback<ModelCategory> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCategory>, response: Response<ModelCategory>
                ) {
                    try {
                        if (response.code() == 200) {
                            mainData =  response.body()!!.data.posts.data
                            AppProgressBar.hideLoaderDialog()

                        }
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding!!.attributeRc.adapter =
                            activity?.let { AdapterCustomer(it, response.body()!!.data.posts.data,this@CustomerFragment) }
                        binding!!.attributeRc.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Data Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.attributeRc.adapter =
                            activity?.let { AdapterCustomer(it, response.body()!!.data.posts.data,this@CustomerFragment) }
                        AppProgressBar.hideLoaderDialog()


                    }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                   // myToast(requireActivity(), "Something went wrong")
                    apiCallCustomer()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun setRecyclerViewAdapter(data: java.util.ArrayList<DataX>) {
        binding!!.attributeRc.apply {
            adapter = context?.let { AdapterCustomer(requireContext(), data,this@CustomerFragment) }
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

        ApiClient.apiService.deleteCustomer(sessionManager.authToken, id, "delete")
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
                            apiCallCustomer()
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


    override fun edit(id: String, nameNew: String,emailNew:String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure want to Edit?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener { sDialog ->
                sDialog.cancel()
                edit = "2"
                name = nameNew
                email = emailNew
                idNew = id
                Navigation.findNavController(binding!!.root).navigate(R.id.createCustomerFragment)

            }
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
            }
            .show()
    }


    companion object {
        var edit = "1"
        var name = ""
        var email = ""
        var idNew = ""
    }

}