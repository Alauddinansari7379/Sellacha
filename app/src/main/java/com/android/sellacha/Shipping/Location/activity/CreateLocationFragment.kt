package com.android.sellacha.Shipping.Location.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.sellacha.Products.categories.Model.ModelCreCatogoryJava
import com.android.sellacha.R
import com.android.sellacha.Shipping.Location.ModelCreateLocation
import com.android.sellacha.Shipping.Location.adapter.AdapterLoaction
import com.android.sellacha.Shipping.LocationFragment
import com.android.sellacha.api.model.categoriesDM
import com.android.sellacha.databinding.FragmentCreateLocationBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateLocationFragment : Fragment() ,AdapterLoaction.Delete{
    private lateinit var binding: FragmentCreateLocationBinding
    private lateinit var sessionManager: SessionManager
    var navController: NavController? = null
    val handler = Handler(Looper.getMainLooper())

    var categoriesArr = ArrayList<categoriesDM>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_location, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateLocationBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        if (LocationFragment.edit =="2"){
            binding!!.btnSave.text="Update"
            binding!!.txtTitle.setText(LocationFragment.title)
        }

         binding.btnSave.setOnClickListener {
            if (binding.txtTitle.text.toString().isEmpty()){
                binding.txtTitle.error="Enter Location"
                binding.txtTitle.requestFocus()
                return@setOnClickListener
            }
            val location= binding.txtTitle.text.toString().trim()
             if(LocationFragment.edit =="2") {
                 editLocation(LocationFragment.idNew,binding.txtTitle.text.toString().trim())
             } else{
                 apiCallCategory(location)

             }
        }


    }


    private fun apiCallCategory(location: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.createLocation(sessionManager.authToken,location)
            .enqueue(object : Callback<ModelCreateLocation> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateLocation>, response: Response<ModelCreateLocation>
                )
                {
                    try {
                        if (response.code() == 500) {
                            myToast(requireContext() as Activity, "Server Error")
                            AppProgressBar.hideLoaderDialog()
                        }
                        else if (response.code() == 200) {
                            myToast(requireActivity(), response.body()!!.data)
                            binding.txtTitle.text.clear()
                             Navigation.findNavController(binding!!.root).navigate(R.id.locationFragment)

                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 401) {
                            myToast(
                                requireContext() as Activity,
                                "Maximum Location Exceeded Please Update Your Plan"
                            )
                            AppProgressBar.hideLoaderDialog()
                        } else {
                            myToast(requireActivity(), response.body()!!.data)
                            //  myToast(requireActivity(), response.message())
                            AppProgressBar.hideLoaderDialog()

                        }
                        // apiCallGetPrePending1()

                        //  binding.prog[
                    //  e.ressBar.progress = 100
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                        myToast(requireActivity(), "Something went wrong")
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCreateLocation>, t: Throwable) {
                     apiCallCategory(location)
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    override fun delete(id: String) {


    }

    override fun edit(id: String, title: String) {
        editLocation(id,title)

    }



    private fun editLocation(id: String,title: String) {

        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.editLocation(sessionManager.authToken, id, title)
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
                            LocationFragment.edit =="1"
                            Navigation.findNavController(binding!!.root).navigate(R.id.locationFragment)

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
                    editLocation(id,title)
                }

            })

    }


}