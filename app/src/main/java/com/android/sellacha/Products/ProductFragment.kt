package com.android.sellacha.Products

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sellacha.R
import com.android.sellacha.adapter.ProductAdapter
import com.android.sellacha.adapter.ProductFilterSelector
import com.android.sellacha.api.ApiResponse
import com.android.sellacha.api.model.filterItemsDM
import com.android.sellacha.api.response.Product.DataItem
import com.android.sellacha.api.response.Product.GetAllProduct
import com.android.sellacha.api.service.MainService
import com.android.sellacha.databinding.FragmentProductBinding
import com.android.sellacha.dialog.AppDialog
import com.android.sellacha.fragment.BaseFragment
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.google.gson.Gson
import com.google.gson.JsonNull
import androidx.core.widget.addTextChangedListener

import java.util.*

class ProductFragment : BaseFragment() {
    var binding: FragmentProductBinding? = null
    var filterNameAdapter: ProductFilterSelector? = null
    var productAdapter: ProductAdapter? = null
    private var productList: MutableList<DataItem?> = ArrayList()
    lateinit var sessionManager: SessionManager
    var filterSelector = 0
    var selectedPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)

        sessionManager= SessionManager(requireContext())

        binding!!.orderList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        productAdapter = context?.let { ProductAdapter(it, productList, 0, binding!!.orderList) }
        productAdapter!!.setOnLoadMoreListener {
            if (productList.size <= 40) {
                productList.add(null)
                productAdapter!!.notifyItemInserted(productList.size - 1)
                Handler().postDelayed({
                    productList.removeAt(productList.size - 1)
                    productAdapter!!.notifyItemRemoved(productList.size)
                    //Generating more data
                    val index = productList.size
                    val end = index + 20
                    for (i in index until end) {
                        productList.add(null)
                    }
                    productAdapter!!.notifyDataSetChanged()
                    productAdapter!!.setLoaded()
                }, 5000)
            } else {
                Toast.makeText(mContext, "Loading data completed", Toast.LENGTH_SHORT).show()
            }
        }

        binding!!.searchTxt.addTextChangedListener { str ->
            setRecyclerViewAdapter(productList.filter {
                it!!.title!!.contains(
                    str.toString(),
                    ignoreCase = true
                )
            } as ArrayList<DataItem>)
        }

        publish
        binding!!.addNewBtn.setOnClickListener { view: View? ->
            findNavController(binding!!.getRoot()).navigate(
                R.id.createProductFragmnet
            )
        }
        binding!!.selectFulfillment.setOnClickListener { view: View? ->

            selectFulfilment(binding!!.selectFulfillment)


        }

        binding!!.btnSubmit.setOnClickListener { view: View? ->

            when (selectedPosition) {
                0 -> {
                    publish
                    //   productAdapter!!.notifySelection(productList, "1");
                }
                1 -> {
                    draft
                    //  productAdapter!!.notifySelection(productList, "2");
                }
                2 -> {
                    trash
                    // productAdapter!!.notifySelection(productList, "0");
                }
            }

        }
        binding!!.searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                filterPlaces(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })


//        productAdapter = new ProductAdapter(getContext(), orderData, 0);
//        binding.orderList.setAdapter(productAdapter);
        return binding!!.root
    }


    private fun filterPlaces(title: String) {
        val filteredPlaces = ArrayList<DataItem>()
        for (bookModel in productList) {
            if (bookModel != null) {
                if (bookModel.title.lowercase(Locale.getDefault()).trim { it <= ' ' }
                        .contains(title.lowercase(Locale.getDefault()).trim { it <= ' ' })) {
                    filteredPlaces.add(bookModel)
                }
            }
        }
        productAdapter!!.filteredList(filteredPlaces)
    }

    private val publish: Unit
        get() {
            AppProgressBar.showLoaderDialog(mContext)
            MainService.getPublishProduct(mContext)
                .observe(viewLifecycleOwner) { response: ApiResponse? ->
                    if (response == null) {
                        errorSnackBar(binding!!.root, getString(R.string.something_wrong))
                    } else {
                        if (response.data !is JsonNull) {
                            if (response.data != null) {
                                val orderResponse =
                                    Gson().fromJson(response.data, GetAllProduct::class.java)
                                productList.clear()
                                productList.addAll(orderResponse.posts.data)
                                getFilterList(orderResponse)
                                productAdapter = context?.let { ProductAdapter(it, productList, 0, binding!!.orderList) }
                                binding!!.orderList.adapter = productAdapter
                            } else {
                                showAlertDialog(
                                    getString(R.string.app_name),
                                    response.message,
                                    "OK",
                                    ""
                                ) { obj: AppDialog -> obj.dismiss() }
                            }
                        } else {
                            errorSnackBar(binding!!.root, response.message)
                        }
                    }
                    AppProgressBar.hideLoaderDialog()
                }
        }
    private val draft: Unit
        get() {
            AppProgressBar.showLoaderDialog(mContext)
            MainService.getDraftProduct(mContext)
                .observe(viewLifecycleOwner) { response: ApiResponse? ->
                    if (response == null) {
                        errorSnackBar(binding!!.root, getString(R.string.something_wrong))
                    } else {
                        if (response.data !is JsonNull) {
                            if (response.data != null) {
                                val orderResponse =
                                    Gson().fromJson(response.data, GetAllProduct::class.java)
                                productList.clear()
                                productList.addAll(orderResponse.posts.data)
                                productAdapter =
                                    context?.let { ProductAdapter(it, productList, 0, binding!!.orderList) }
                                binding!!.orderList.adapter = productAdapter
                            } else {
                                showAlertDialog(
                                    getString(R.string.app_name),
                                    response.message,
                                    "OK",
                                    ""
                                ) { obj: AppDialog -> obj.dismiss() }
                            }
                        } else {
                            errorSnackBar(binding!!.root, response.message)
                        }
                    }
                    AppProgressBar.hideLoaderDialog()
                }
        }
    private fun setRecyclerViewAdapter(data: ArrayList<DataItem>) {
        binding!!.orderList.apply {
            productAdapter =
                context?.let { ProductAdapter(it, data, 0, binding!!.orderList) }
            binding!!.orderList.adapter = productAdapter
         }
    }
    private val incomplete: Unit
        get() {
            AppProgressBar.showLoaderDialog(mContext)
            MainService.getIncompleteProduct(mContext)
                .observe(viewLifecycleOwner) { response: ApiResponse? ->
                    if (response == null) {
                        errorSnackBar(binding!!.root, getString(R.string.something_wrong))
                    } else {
                        if (response.data !is JsonNull) {
                            if (response.data != null) {
                                val orderResponse =
                                    Gson().fromJson(response.data, GetAllProduct::class.java)
                                productList.clear()
                                productList.addAll(orderResponse.posts.data)
                                productAdapter =
                                    context?.let { ProductAdapter(it, productList, 0, binding!!.orderList) }
                                binding!!.orderList.adapter = productAdapter
                            } else {
                                AppProgressBar.hideLoaderDialog()
                                showAlertDialog(
                                    getString(R.string.app_name),
                                    response.message,
                                    "OK",
                                    ""
                                ) { obj: AppDialog -> obj.dismiss() }
                            }
                        } else {
                            AppProgressBar.hideLoaderDialog()
                            errorSnackBar(binding!!.root, response.message)
                        }
                    }
                }
        }
    private val trash: Unit
        get() {
            AppProgressBar.showLoaderDialog(mContext)
            MainService.getTrash(mContext).observe(viewLifecycleOwner) { response: ApiResponse? ->
                if (response == null) {
                    errorSnackBar(binding!!.root, getString(R.string.something_wrong))
                } else {
                    if (response.data !is JsonNull) {
                        if (response.data != null) {
                            val orderResponse =
                                Gson().fromJson(response.data, GetAllProduct::class.java)
                            productList.clear()
                            productList.addAll(orderResponse.posts.data)
                            productAdapter =
                                context?.let { ProductAdapter(it, productList, 0, binding!!.orderList) }
                            binding!!.orderList.adapter = productAdapter
                        } else {
                            showAlertDialog(
                                getString(R.string.app_name),
                                response.message,
                                "OK",
                                ""
                            ) { obj: AppDialog -> obj.dismiss() }
                        }
                    } else {
                        errorSnackBar(binding!!.root, response.message)
                    }
                }
                AppProgressBar.hideLoaderDialog()
            }
        }

    private val searchProduct: Unit
        get() {
            AppProgressBar.showLoaderDialog(mContext)
            MainService.getTrash(mContext).observe(viewLifecycleOwner) { response: ApiResponse? ->
                if (response == null) {
                    errorSnackBar(binding!!.root, getString(R.string.something_wrong))
                } else {
                    if (response.data !is JsonNull) {
                        if (response.data != null) {
                            val orderResponse =
                                Gson().fromJson(response.data, GetAllProduct::class.java)
                            productList.clear()
                            productList.addAll(orderResponse.posts.data)
                            productAdapter = context?.let { ProductAdapter(it, productList, 0, binding!!.orderList) }
                            binding!!.orderList.adapter = productAdapter
                        } else {
                            showAlertDialog(
                                getString(R.string.app_name),
                                response.message,
                                "OK",
                                ""
                            ) { obj: AppDialog -> obj.dismiss() }
                        }
                    } else {
                        errorSnackBar(binding!!.root, response.message)
                    }
                }
                AppProgressBar.hideLoaderDialog()
            }
        }

    private fun getFilterList(orderResponse: GetAllProduct) {
        val myListData = arrayOf(
            filterItemsDM("Publish", orderResponse.actives),
            filterItemsDM("Draft", orderResponse.drafts),
            filterItemsDM("Incomplete", orderResponse.incomplete),
            filterItemsDM("Trash", orderResponse.trash)
        )
        binding!!.filterRv.visibility = View.VISIBLE
        binding!!.filterRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,
            false
        )
        filterNameAdapter = ProductFilterSelector(context, myListData, 0, this)
        binding!!.filterRv.adapter = filterNameAdapter
    }

    fun filterSelector(position: Int) {
        filterSelector = position
        when (position) {
            0 -> {
                publish
                //   productAdapter!!.notifySelection(productList, "1");
            }
            1 -> {
                draft
                  //  productAdapter!!.notifySelection(productList, "2");
            }
            2 -> {
                incomplete
                  // productAdapter!!.notifySelection(productList, "3");
            }
            3 -> {
                trash
                  // productAdapter!!.notifySelection(productList, "0");
            }
        }
    }

    private fun selectFulfilment(textView: TextView) {
        val builderSingle = AlertDialog.Builder(context)
        builderSingle.setTitle("Filter Product")
        val arrayAdapter = ArrayAdapter<String>(activity!!, R.layout.dialog_txt_item)
        arrayAdapter.add("Published")
        arrayAdapter.add("Draft")
        arrayAdapter.add("Trash")
        builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
        builderSingle.setAdapter(arrayAdapter) { dialog, which ->
            val strName = arrayAdapter.getItem(which)
            selectedPosition=which
            textView.text = strName
        }
        builderSingle.show()
    } //    public void getAllProduct() {
    //        AppProgressBar.showLoaderDialog(mContext);
    //        MainService.getAllProduct(mContext).observe(getViewLifecycleOwner(), response -> {
    //            if (response == null) {
    //                errorSnackBar(binding.getRoot(), getString(R.string.something_wrong));
    //            } else {
    //                if (!(response.getData() instanceof JsonNull)) {
    //                    if (response.getData() != null) {
    //                        GetAllProduct orderResponse = new Gson().fromJson(response.getData(), GetAllProduct.class);
    //                        productList.clear();
    //                        productList.addAll(orderResponse.getPosts().getData());
    //                        getFilterList(orderResponse);
    //                        List<DataItem> tempList = new ArrayList<>();
    //                        for (int x = 0; x < productList.size(); x++) {
    //                            if (productList.get(x).getStatus().equalsIgnoreCase("1")) {
    //                                tempList.add(productList.get(x));
    //                            }
    //                        }
    //                        productAdapter = new ProductAdapter(getContext(), tempList, 0);
    //                        binding.orderList.setAdapter(productAdapter);
    //                    } else {
    //                        showAlertDialog(getString(R.string.app_name), response.getMessage(), "OK", "", DialogFragment::dismiss);
    //                    }
    //                } else {
    //                    errorSnackBar(binding.getRoot(), response.getMessage());
    //                }
    //            }
    //            AppProgressBar.hideLoaderDialog();
    //        });
    //    }
}