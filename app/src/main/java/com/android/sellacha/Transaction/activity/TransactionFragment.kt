package com.android.sellacha.Transaction.activity

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
import com.android.sellacha.Transaction.Model.ModelTransaction
import com.android.sellacha.Transaction.adapter.AdapterTransaction
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

class TransactionFragment : Fragment() {
    lateinit var binding: FragmentTransactionBinding
    private lateinit var sessionManager: SessionManager
    val handler = Handler(Looper.getMainLooper())

    val authorization = "Bearer 5|MuaOrW4WznVlYnCgjGRcJlopUqd2RJztSKcjorLG"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionBinding.bind(view)
        sessionManager = SessionManager(requireContext())


        CoroutineScope(Dispatchers.IO).launch {
            Log.d("FetchContact89", "fetchContacts: coroutine start")


        }
        binding.imgSearch.setOnClickListener {
            if (binding.edtTransId.text.toString().isEmpty()){
                binding.edtTransId.error="Enter Transaction Id"
                binding.edtTransId.requestFocus()
                return@setOnClickListener
            }
            val transactionId=binding.edtTransId.text.toString().trim()
            apiCallTransactionByTransId(transactionId)
        }
        apiCallTransaction()

    }

    private fun apiCallTransaction() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getTransaction(sessionManager.authToken)
            .enqueue(object : Callback<ModelTransaction> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelTransaction>, response: Response<ModelTransaction>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.orders.data.isEmpty()) {
                        binding.transactionsRc.adapter =
                            activity?.let { AdapterTransaction(it, response.body()!!) }
                        binding.transactionsRc.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Transaction Found")
                        AppProgressBar.hideLoaderDialog()
                    } else {
                        binding.transactionsRc.adapter =
                            activity?.let { AdapterTransaction(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelTransaction>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
    private fun apiCallTransactionByTransId(transactionId: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getTransactionByTransId(sessionManager.authToken,transactionId)
            .enqueue(object : Callback<ModelTransaction> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelTransaction>, response: Response<ModelTransaction>
                ) {
                    if (response.code() == 500) {
                        myToast(requireActivity(), "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.data.orders.data.isEmpty()) {
                        binding.transactionsRc.adapter =
                            activity?.let { AdapterTransaction(it, response.body()!!) }
                        binding.transactionsRc.adapter!!.notifyDataSetChanged()
                        myToast(requireActivity(), "No Transaction Found")
                        AppProgressBar.hideLoaderDialog()
                    } else {
                        binding.transactionsRc.adapter =
                            activity?.let { AdapterTransaction(it, response.body()!!) }
                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelTransaction>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}