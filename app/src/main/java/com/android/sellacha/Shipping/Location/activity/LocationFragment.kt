package com.android.sellacha.Shipping

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Products.categories.Model.ModelCreCatogoryJava
import com.android.sellacha.R
import com.android.sellacha.Shipping.Location.adapter.AdapterLoaction
import com.android.sellacha.databinding.FragmentLocationBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationFragment : Fragment(),AdapterLoaction.Delete {
    lateinit var binding: FragmentLocationBinding
    private lateinit var sessionManager: SessionManager
    private var mainData = ArrayList<DataX>()

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

        binding!!.searchTxt.addTextChangedListener { str ->
            setRecyclerViewAdapter(mainData.filter {
                it!!.name!!.contains(
                    str.toString(),
                    ignoreCase = true
                )
            } as java.util.ArrayList<DataX>)
        }

        binding!!.CreateLocation.setOnClickListener {
            edit = "1"
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
                    try {
                        if (response.code() == 200) {
                            mainData = response.body()!!.data.posts.data
                            AppProgressBar.hideLoaderDialog()

                        }
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")

                        } else if (response.body()!!.data.posts.data.isEmpty()) {
                            binding!!.locationList.adapter =
                                activity?.let { AdapterLoaction(it,response.body()!!.data.posts.data,this@LocationFragment) }
                            binding!!.locationList.adapter!!.notifyDataSetChanged()
                            myToast(requireActivity(), "No Location Found")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding!!.locationList.adapter =
                                activity?.let { AdapterLoaction(it, response.body()!!.data.posts.data,this@LocationFragment) }
                            AppProgressBar.hideLoaderDialog()


                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                   // myToast(requireActivity(), "Something went wrong")
                    apiCallLocation()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
    private fun setRecyclerViewAdapter(data: java.util.ArrayList<DataX>) {
        binding!!.locationList.apply {
            adapter = context?.let { AdapterLoaction(requireContext(), data,this@LocationFragment) }
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
                deleteLocation(id)
            }
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
            }
            .show()    }

    override fun edit(id: String, titleNew: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure want to Edit?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener { sDialog ->
                sDialog.cancel()
                edit = "2"
                title = titleNew
                idNew = id
                Navigation.findNavController(binding!!.root).navigate(R.id.CreateLocationFragment)

            }
            .setCancelClickListener { sDialog ->
                sDialog.cancel()
            }
            .show()
    }
    private fun deleteLocation(id: String) {

        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.deleteLocation(sessionManager.authToken, id, "delete")
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
                            apiCallLocation()

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
                    deleteLocation(id)
                }

            })

    }
    companion object {
        var edit = ""
        var title = ""
        var idNew = ""
    }

}
