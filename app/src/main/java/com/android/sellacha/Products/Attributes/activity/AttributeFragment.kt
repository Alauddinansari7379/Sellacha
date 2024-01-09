package com.android.sellacha.Products.Attributes.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.Attributes.activity.MOdel.Post
import com.android.sellacha.Products.Attributes.adapter.AdapterAttribute
import com.android.sellacha.Products.Coupons.MOdel.ModelCreateCoupon
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentAttributeBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttributeFragment : Fragment(),AdapterAttribute.Delete {
    var binding: FragmentAttributeBinding? = null
    private lateinit var sessionManager: SessionManager
    private var mainData = java.util.ArrayList<Post>()

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
        binding!!.createAttribute.setOnClickListener {
            edit="1"
            Navigation.findNavController(binding!!.root).navigate(R.id.createAttributeFragment)
        }

        binding!!.searchTxt.addTextChangedListener { str ->
            setRecyclerViewAdapter(mainData.filter {
                it!!.name!!.contains(
                    str.toString(),
                    ignoreCase = true
                )
            } as java.util.ArrayList<Post>)
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            Log.d("FetchContact89", "fetchContacts: coroutine start")
//
//
//        }
    }

    private fun apiCallAttribute() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getAttribute(sessionManager.authToken)
            .enqueue(object : Callback<ModelAttributes> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelAttributes>, response: Response<ModelAttributes>
                ) {
                    try {
                        if (response.code() == 200) {
                            mainData = response.body()!!.data.posts
                            AppProgressBar.hideLoaderDialog()

                        }
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")

                        } else if (response.body()!!.data.posts.isEmpty()) {
                            binding!!.recyclerView.adapter =
                                activity?.let { AdapterAttribute(it, response.body()!!.data.posts,this@AttributeFragment) }
                            binding!!.recyclerView.adapter!!.notifyDataSetChanged()
                            myToast(requireActivity(), "No Data Found")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding!!.recyclerView.adapter =
                                activity?.let { AdapterAttribute(it, response.body()!!.data.posts,this@AttributeFragment) }
                            AppProgressBar.hideLoaderDialog()

                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelAttributes>, t: Throwable) {
                    //myToast(requireActivity(), "Something went wrong")
                    apiCallAttribute()
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
    private fun setRecyclerViewAdapter(data: java.util.ArrayList<Post>) {
        binding!!.recyclerView.apply {
            adapter = context?.let { AdapterAttribute(requireContext(), data,this@AttributeFragment) }
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
                            apiCallAttribute()
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


    override fun edit(name: String, id: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure want to edit?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener { sDialog ->
                sDialog.cancel()
                 edit ="2"
                presonName =name
                idNew =id
                Navigation.findNavController(binding!!.root).navigate(R.id.createAttributeFragment)

            }
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
            }
            .show()
     }

    companion object{
        var edit="1"
        var presonName=""
        var idNew=""

    }
}