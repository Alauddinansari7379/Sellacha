package com.android.sellacha.Report

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.R
import com.android.sellacha.Report.model.ModelReort
import com.android.sellacha.Transaction.adapter.AdapterReport
import com.android.sellacha.databinding.FragmentReportBinding
import com.android.sellacha.helper.currentDate
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment() {
    lateinit var binding: FragmentReportBinding
    private lateinit var sessionManager: SessionManager
    var mydilaog: Dialog? = null
    var startdate = ""
    var endtdate = ""
    val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportBinding.bind(view)
        sessionManager = SessionManager(requireContext())


        CoroutineScope(Dispatchers.IO).launch {
            Log.d("FetchContact89", "fetchContacts: coroutine start")
        }
        apiCallReport()

        binding.tvEndDate.text = currentDate
         endtdate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        binding.imgSearch.setOnClickListener {
            apiCallFilterReport(startdate, endtdate)
        }

        binding.imgRefresh.setOnClickListener {
            apiCallReport()
        }

        mydilaog?.setCanceledOnTouchOutside(false)
        mydilaog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val newCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                DateFormat.getDateInstance().format(newDate.time)
                // val Date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(newDate.time)
                binding!!.tvStartDate.text =
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(newDate.time)
                startdate =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate.time)



                Log.e(ContentValues.TAG, "startdate:>>$startdate")
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        //datePicker.datePicker.minDate = System.currentTimeMillis() - 1000;

        binding!!.tvStartDate.setOnClickListener {
            datePicker.show()
        }


        mydilaog?.setCanceledOnTouchOutside(false)
        mydilaog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val newCalendar1 = Calendar.getInstance()
        val datePicker1 = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                DateFormat.getDateInstance().format(newDate.time)
                // val Date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(newDate.time)
                binding!!.tvEndDate.text =
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(newDate.time)
                endtdate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate.time)

                Log.e(ContentValues.TAG, "enddate:>>$endtdate")
            },
            newCalendar1[Calendar.YEAR],
            newCalendar1[Calendar.MONTH],
            newCalendar1[Calendar.DAY_OF_MONTH]
        )
        //datePicker.datePicker.minDate = System.currentTimeMillis() - 1000;

        binding!!.tvEndDate.setOnClickListener {
            datePicker1.show()
        }
    }

    private fun apiCallReport() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getReport(sessionManager.authToken)
            .enqueue(object : Callback<ModelReort> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelReort>, response: Response<ModelReort>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.orders.data.isEmpty()) {
                        binding.transactionsRc.adapter =
                            activity?.let { AdapterReport(it, response.body()!!) }
                        binding.transactionsRc.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Report Found")
                        AppProgressBar.hideLoaderDialog()
                    } else {
                        binding.transactionsRc.adapter =
                            activity?.let { AdapterReport(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelReort>, t: Throwable) {
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallReport()
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun apiCallFilterReport(startdate: String, endtdate: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getFilterReport(sessionManager.authToken, startdate, endtdate)
            .enqueue(object : Callback<ModelReort> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelReort>, response: Response<ModelReort>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.orders.data.isEmpty()) {
                        binding.transactionsRc.adapter =
                            activity?.let { AdapterReport(it, response.body()!!) }
                        binding.transactionsRc.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Report Found")
                        AppProgressBar.hideLoaderDialog()
                    } else {
                        binding.transactionsRc.adapter =
                            activity?.let { AdapterReport(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelReort>, t: Throwable) {
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallFilterReport(startdate, endtdate)
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}