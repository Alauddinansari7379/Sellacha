package com.android.sellacha.Shipping.ShippingPrice.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.Products.categories.Model.DataX
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.Shipping.ShippingPrice.model.ModelCreateShipping
import com.android.sellacha.databinding.FragmentCreateShippingMethodBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateShippingMethod : Fragment() {
    lateinit var binding: FragmentCreateShippingMethodBinding
    private lateinit var sessionManager: SessionManager
    var locationlist = ArrayList<String>()
    private var mainData = ArrayList<DataX>()
    var location=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_shipping_method, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateShippingMethodBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        if (ShippingFragment.edit =="2"){
            binding!!.btnSave.text="Update"
            apiCallLocation()
            binding.spinnerLocation.visibility=View.VISIBLE
            binding.txtLocation.visibility=View.GONE
            binding!!.txtTitle.setText(ShippingFragment.title)
            binding!!.txtPrice.setText(ShippingFragment.price)
         }

        binding.btnSave.setOnClickListener {
            if (binding.txtTitle.text.toString().isEmpty()) {
                binding.txtTitle.error = "Enter Title"
                binding.txtTitle.requestFocus()
                return@setOnClickListener
            }
            if (binding.txtPrice.text.toString().isEmpty()) {
                binding.txtPrice.error = "Enter Price"
                binding.txtPrice.requestFocus()
                return@setOnClickListener
            }
//            if (binding.txtLocation.text.toString().isEmpty()) {
//                binding.txtLocation.error = "Enter Location"
//                binding.txtLocation.requestFocus()
//                return@setOnClickListener
//            }
            val tile = binding.txtTitle.text.toString().trim()
            val price = binding.txtPrice.text.toString().trim()
            locationlist.add(binding.txtLocation.text.toString().trim())

            if (ShippingFragment.edit =="2"){

                apiCallEditShipping(tile,price)
            }else{
                apiCallCreateShipping(tile, price)

            }
        }


    }

    private fun apiCallCreateShipping(tile: String, price: String,) {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.createShipping(sessionManager.authToken, tile, price, binding.txtLocation.text.toString().trim())
            .enqueue(object : Callback<ModelCreateShipping> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateShipping>, response: Response<ModelCreateShipping>
                ) {
                    try{
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Created")
                        binding.txtTitle.text.clear()
                        binding.txtPrice.text.clear()
                        binding.txtLocation.text.clear()
                        AppProgressBar.hideLoaderDialog()
                        Navigation.findNavController(binding!!.root).navigate(R.id.ShippingFragmentFragment)


                    } else {
                        myToast(requireActivity(), response.message())
                        binding.txtTitle.text.clear()
                        binding.txtPrice.text.clear()
                        binding.txtLocation.text.clear()
                        AppProgressBar.hideLoaderDialog()
                    }

                    }catch (e:Exception){
                        e.printStackTrace()
                        myToast(requireActivity(), "Something went wrong")
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCreateShipping>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
    private fun apiCallEditShipping(tile: String, price: String,) {
        AppProgressBar.showLoaderDialog(requireContext())
        ApiClient.apiService.editShipping(sessionManager.authToken, tile, price, location,ShippingFragment.idNew)
            .enqueue(object : Callback<ModelCreateShipping> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateShipping>, response: Response<ModelCreateShipping>
                ) {
                    try{
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Updated")
                        binding.txtTitle.text.clear()
                        binding.txtPrice.text.clear()
                        binding.txtLocation.text.clear()
                        AppProgressBar.hideLoaderDialog()
                        ShippingFragment.edit ="1"
                        Navigation.findNavController(binding!!.root).navigate(R.id.ShippingFragmentFragment)

                    } else {
                        myToast(requireActivity(), response.message())
                        binding.txtTitle.text.clear()
                        binding.txtPrice.text.clear()
                        binding.txtLocation.text.clear()
                        AppProgressBar.hideLoaderDialog()
                    }

                    }catch (e:Exception){
                        e.printStackTrace()
                        myToast(requireActivity(), "Something went wrong")
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCreateShipping>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
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

                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")

                        } else if (response.body()!!.data.posts.data.isEmpty()) {
                             AppProgressBar.hideLoaderDialog()

                        }
                        if (response.code() == 200) {
                            mainData = response.body()!!.data.posts.data
                        mainData = response.body()!!.data.posts.data
                        if (mainData != null) {

                            //spinner code start
                            val items = arrayOfNulls<String>(mainData.size)

                            for (i in mainData!!.indices) {

                                items[i] = mainData!![i].name
                            }
                            val adapter: ArrayAdapter<String?> =
                                ArrayAdapter(requireContext(), R.layout.simple_list_item_1, items)
                             binding.spinnerLocation.adapter = adapter
                         //   binding.spinnerLocation.setSelection(items.indexOf(sessionManager.qualification.toString()));
                            //   binding.spinnerDegree.setSelection(sessionManager.qualification.toString().toInt())
                            AppProgressBar.hideLoaderDialog()


                            binding.spinnerLocation.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        adapterView: AdapterView<*>?,
                                        view: View,
                                        i: Int,
                                        l: Long
                                    ) {
                                        location = mainData!![i].name
                                         // Toast.makeText(this@RegirstrationTest, "" + id, Toast.LENGTH_SHORT).show()
                                    }

                                    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                                }

                        }
                        }


                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelCategory>, t: Throwable) {
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallLocation()
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        ShippingFragment.edit ="1"
    }
}