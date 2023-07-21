package com.android.sellacha.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sellacha.R
import com.android.sellacha.activity.CheckOut
import com.android.sellacha.adapter.OrderAdapter
import com.android.sellacha.adapter.OrderFilterSelector
import com.android.sellacha.api.ApiResponse
import com.android.sellacha.api.model.filterItemsDM
import com.android.sellacha.api.response.GetAllorder.OrderItem
import com.android.sellacha.api.response.GetAllorder.OrderResponse
import com.android.sellacha.api.service.MainService
import com.android.sellacha.databinding.FilterDailogBinding
import com.android.sellacha.databinding.FragmentOrderBinding
import com.android.sellacha.dialog.AppDialog
import com.android.sellacha.utils.AppProgressBar
import com.google.gson.Gson
import com.google.gson.JsonNull
import java.text.SimpleDateFormat
import java.util.*

class OrderFragment : BaseFragment() {
    var binding: FragmentOrderBinding? = null
    var filterNameAdapter: OrderFilterSelector? = null
    var orderAdapter: OrderAdapter? = null
    var orderList: MutableList<OrderItem> = ArrayList()
    private lateinit var myListData: Array<filterItemsDM>
    var filterDialog: Dialog? = null
    var filterSelector = 0
    var selectionPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
            allOrder
            //   getFilterList();
            filterDialog = Dialog(mContext)
            binding!!.orderList.layoutManager = LinearLayoutManager(
                activity, LinearLayoutManager.VERTICAL, false)
            binding!!.searchTxt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    filterPlaces(charSequence.toString())
                    if (binding!!.searchTxt.text.toString().isEmpty()) {
                        filterSelector(filterSelector)
                    }
                }

                override fun afterTextChanged(editable: Editable) {}
            })

            binding!!.searchCloseBtn.setOnClickListener {
                binding!!.searchTxt.setText("")
            }
            binding!!.createOrder.setOnClickListener {
                Navigation.findNavController(binding!!.root).navigate(R.id.CreateOrderShow)
              //  startActivity(Intent(mContext, CheckOut::class.java))
            }
            binding!!.selectFulfillment.setOnClickListener {
                filterDialog(binding!!.selectFulfillment)

            }

            binding!!.btnSubmit.setOnClickListener {
                when (selectionPosition) {
//                    0 -> {
//                        allOrder
//                    }
                    0 -> {
                        orderAdapter!!.notifySelection(orderList, "pending")
                    }
                    1 -> {
                        orderAdapter!!.notifySelection(orderList, "processing")
                    }
                    2 -> {
                        orderAdapter!!.notifySelection(orderList, "pickup")
                    }
                    3 -> {
                        orderAdapter!!.notifySelection(orderList, "completed")
                    }
                    4 -> {
                        orderAdapter!!.notifySelection(orderList, "archived")
                    }
                    5 -> {
                        orderAdapter!!.notifySelection(orderList, "canceled")

                    }
                }
            }

            binding!!.filterBtn.setOnClickListener { filterDialog() }
        }
        return binding!!.root
    }

    private fun filterPlaces(title: String) {
        val filteredPlaces = ArrayList<OrderItem>()
        for (bookModel in orderList) {
            if (bookModel.orderNo.lowercase(Locale.getDefault()).trim { it <= ' ' }
                    .contains(title.lowercase(Locale.getDefault()).trim { it <= ' ' })) {
                filteredPlaces.add(bookModel)
            }
        }
        orderAdapter!!.filteredList(filteredPlaces)
    }

    private val allOrder: Unit
        get() {
            AppProgressBar.showLoaderDialog(mContext)
            MainService.getAllOrder(mContext)
                .observe(viewLifecycleOwner) { response: ApiResponse? ->
                    if (response == null) {
                        errorSnackBar(binding!!.root, getString(R.string.something_wrong))
                    } else {
                        if (response.data !is JsonNull) {
                            if (response.data != null) {
                                val orderResponse =
                                    Gson().fromJson(response.data, OrderResponse::class.java)
                                orderList.clear()
                                orderList.addAll(orderResponse.orders.data)
                                getFilterList(orderResponse)
                                orderAdapter = context?.let { OrderAdapter(it, orderList, 0)
                                }
                                binding!!.orderList.adapter = orderAdapter
                            } else {
                                showAlertDialog(getString(R.string.app_name), response.message, "OK", "") { obj: AppDialog -> obj.dismiss() }
                            }
                        } else {
                            errorSnackBar(binding!!.root, response.message)
                        }
                    }
                    AppProgressBar.hideLoaderDialog()
                }
        }

    private fun getFilterOrder(id: Int, status: String?, start: String?, end: String?) {
        AppProgressBar.showLoaderDialog(mContext)
        MainService.getFilterOrder(mContext, id, status, start, end)
            .observe(viewLifecycleOwner) { response: ApiResponse? ->
                if (response == null) {
                    errorSnackBar(binding!!.root, getString(R.string.something_wrong))
                } else {
                    if (response.data !is JsonNull) {
                        if (response.data != null) {
                            val orderResponse =
                                Gson().fromJson(response.data, OrderResponse::class.java)
                            orderList.clear()
                            orderList.addAll(orderResponse.orders.data)
                            orderAdapter = context?.let { OrderAdapter(it, orderList, 0) }
                            binding!!.orderList.adapter = orderAdapter
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

    fun filterSelector(position: Int) {
        filterSelector = position
        when (position) {
            0 -> {
                allOrder
            }
            1 -> {
                orderAdapter!!.notifySelection(orderList, "pending")
            }
            2 -> {
                orderAdapter!!.notifySelection(orderList, "processing")
            }
            3 -> {
                orderAdapter!!.notifySelection(orderList, "pickup")
            }
            4 -> {
                orderAdapter!!.notifySelection(orderList, "completed")
            }
            5 -> {
                orderAdapter!!.notifySelection(orderList, "canceled")
            }
            6 -> {
                orderAdapter!!.notifySelection(orderList, "archived")
            }
        }
    }

    private fun getFilterList(orderResponse: OrderResponse) {
        myListData = arrayOf(
            filterItemsDM("All", orderResponse.orders.data.size),
            filterItemsDM("Awaiting Processing", orderResponse.pendings),
            filterItemsDM("Processing", orderResponse.processing),
            filterItemsDM("Ready For Pickup", orderResponse.pickup),
            filterItemsDM("Completed", orderResponse.completed),
            filterItemsDM("Cancelled", orderResponse.canceled),
            filterItemsDM("Archived", orderResponse.archived)
        )


        binding!!.filterRv.visibility = View.VISIBLE
        binding!!.filterRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        filterNameAdapter = OrderFilterSelector(context, myListData, 0, this)
        binding!!.filterRv.adapter = filterNameAdapter
    }

    private fun filterDialog(textView: TextView) {
        val builderSingle = AlertDialog.Builder(context)
        builderSingle.setTitle("Select fulfillment")

        val arrayAdapter = ArrayAdapter<String>(activity!!, R.layout.dialog_txt_item)

        arrayAdapter.add("Awaiting processing")
        arrayAdapter.add("Processing")
        arrayAdapter.add("Ready-for-pickup")
        arrayAdapter.add("Completed")
        arrayAdapter.add("Archived")
        arrayAdapter.add("Canceled")
        builderSingle.setNegativeButton("cancel") { dialog, _ -> dialog.dismiss()

        }
        builderSingle.setAdapter(arrayAdapter) { pos, which ->
            val strName = arrayAdapter.getItem(which)
            selectionPosition =which
            Log.e("posiction",which.toString())
            textView.text = strName
        }
        builderSingle.show()
    }

    private fun paymentDialog(textView: TextView) {
        val builderSingle = AlertDialog.Builder(context)
        builderSingle.setTitle("Select Payment Status")
        val arrayAdapter = ArrayAdapter<String>(activity!!, R.layout.dialog_txt_item)
        arrayAdapter.add("Pending")
        arrayAdapter.add("Complete")
        arrayAdapter.add("Cancel")
        builderSingle.setNegativeButton("cancel") { dialog, _ -> dialog.dismiss() }
        builderSingle.setAdapter(arrayAdapter) { _, which ->
            val strName = arrayAdapter.getItem(which)
            textView.text = strName
        }
        builderSingle.show()
    }

    private fun filterDialog() {
        val payment = intArrayOf(0)
        val sDialog = DataBindingUtil.inflate<FilterDailogBinding>(
            LayoutInflater.from(mContext),
            R.layout.filter_dailog,
            null,
            false
        )
        filterDialog!!.setContentView(sDialog.root)
        filterDialog!!.setCancelable(true)
        filterDialog!!.setCanceledOnTouchOutside(true)
        sDialog.startingDateTxt.setOnClickListener { datePicker(sDialog.startingDateTxt) }
        sDialog.endingDateTxt.setOnClickListener { datePicker(sDialog.endingDateTxt) }
        sDialog.fulfilmentstatusTxt.setOnClickListener {
            filterDialog(sDialog.fulfilmentstatusTxt)
        }
        sDialog.fulfilmentstatusTxt.setOnClickListener { filterDialog(sDialog.fulfilmentstatusTxt) }
        sDialog.paymentStatusTxt.setOnClickListener { paymentDialog(sDialog.paymentStatusTxt) }
        sDialog.cancelBtn.setOnClickListener {
            sDialog.startingDateTxt.text = ""
            sDialog.endingDateTxt.text = ""
            sDialog.fulfilmentstatusTxt.text = ""
            sDialog.paymentStatusTxt.text = ""
        }
        sDialog.okBtn.setOnClickListener {
            filterDialog!!.dismiss()
            if (sDialog.paymentStatusTxt.text.toString() == "Pending") {
                payment[0] = 2
            } else if (sDialog.paymentStatusTxt.text.toString() == "Complete") {
                payment[0] = 1
            } else if (sDialog.paymentStatusTxt.text.toString() == "Cancel") {
            }
            getFilterOrder(
                payment[0],
                sDialog.fulfilmentstatusTxt.toString(),
                sDialog.startingDateTxt.toString(),
                sDialog.endingDateTxt.toString()
            )
        }
        filterDialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        filterDialog!!.window!!.setGravity(Gravity.CENTER)
        filterDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        filterDialog!!.show()
    }

    private fun datePicker(textView: TextView) {
        val myCalendar = Calendar.getInstance()
        val date =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val myFormat = "yyyy-MM-dd" // your format
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                textView.text = sdf.format(myCalendar.time)
            }
        DatePickerDialog(
            mContext,
            R.style.datepicker,
            date,
            myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        ).show()
    }
}