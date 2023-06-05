package com.android.sellacha.Products.Inventory.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sellacha.Products.Coupons.MOdel.ModelCoupons
import com.android.sellacha.Products.Coupons.adapter.AdapterCoupons
import com.android.sellacha.Products.Inventory.activity.adapter.AdapterInventory
import com.android.sellacha.Products.Inventory.activity.model.Modelinventory
import com.android.sellacha.Products.Inventory.activity.model.Term
import com.android.sellacha.R
import com.android.sellacha.adapter.InventoryAdapter
import com.android.sellacha.adapter.OrderFilterSelector
import com.android.sellacha.api.model.filterItemsDM
import com.android.sellacha.api.model.inventoryDM
import com.android.sellacha.api.model.inventoryTypeDM
import com.android.sellacha.databinding.FragmentCouponsBinding
import com.android.sellacha.databinding.FragmentInventoryBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class InventoryFragment : Fragment() {
    var binding: FragmentInventoryBinding? = null
    private lateinit var sessionManager: SessionManager
    private lateinit var inventoryList: Array<Term>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inventory, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInventoryBinding.bind(view)
        sessionManager= SessionManager(requireContext())


        apiCallInventory()


    }

    private fun apiCallInventory() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.inventory(sessionManager.authToken)
            .enqueue(object : Callback<Modelinventory> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<Modelinventory>, response: Response<Modelinventory>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.posts.data.isEmpty()) {
                        binding!!.inventoryRv.adapter =
                            activity?.let {

                                AdapterInventory(it, response.body()!!)
                            }
                        binding!!.inventoryRv.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Data Found")
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        binding!!.inventoryRv.adapter =
                            activity?.let { AdapterInventory(it, response.body()!!)

                            }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<Modelinventory>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallInventory()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
//    private fun filter(text: String) {
//        // creating a new array list to filter our data.
//        val filteredlist = ArrayList<Term>()
//        filteredlist.clear()
//
//        // running a for loop to compare elements.
//        for (item in inventoryList) {
//            // checking if the entered string matched with any item of our recycler view.
//            if (item.title.contains(text)) {
//                Log.e(ContentValues.TAG, "filter:>>>>>>>>>>>>>>>>>> ${item.orderno}/$text" )
//                // if the item is matched we are
//                // adding it to our filtered list.
//                filteredlist.add(item)
//                Log.e(ContentValues.TAG, "filterSize:>>>>>>>>>>>>>>>>>> ${filteredlist.size}" )
//
//                binding.rvorderlistplaning.adapter= AdapterInventory(requireContext(),Modelinventory())
//                binding.tvCountp.text=filteredlist.size.toString()
//            }
//        }
////        if (filteredlist.isEmpty()) {
////            // if no item is added in filtered list we are
////            // displaying a toast message as no data found.
////            myToast(this,"No Data Found")
////        } else {
////            // at last we are passing that filtered
////            // list to our adapter class.
//////            AdapterOrderAllocation.filterList(filteredlist)
////        }
//    }

}