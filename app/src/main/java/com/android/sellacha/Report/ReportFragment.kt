package com.android.sellacha.Report

import android.annotation.SuppressLint
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
import com.android.sellacha.Transaction.Model.ModelTransaction
import com.android.sellacha.Transaction.adapter.AdapterReport
import com.android.sellacha.Transaction.adapter.AdapterTransaction
import com.android.sellacha.databinding.FragmentReportBinding
import com.android.sellacha.databinding.FragmentTransactionBinding
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

class ReportFragment : Fragment() {
    lateinit var binding: FragmentReportBinding
    private lateinit var sessionManager: SessionManager
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
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}