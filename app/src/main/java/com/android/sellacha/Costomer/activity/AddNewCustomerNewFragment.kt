package com.android.sellacha.Costomer.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.Costomer.model.ModelCreateCus
import com.android.sellacha.R
import com.android.sellacha.adapter.OrderFilterSelector
import com.android.sellacha.databinding.FragmentAddNewCustomerBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewCustomerNewFragment : Fragment() {
    lateinit var binding: FragmentAddNewCustomerBinding
    var filterSelectorAdapter: OrderFilterSelector? = null
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_new_customer, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewCustomerBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        if (CustomerFragment.edit == "2") {
            binding!!.saveBtn.text = "Edit"
            binding!!.nameTxt.setText(CustomerFragment.name)
            binding!!.emailTxt.setText(CustomerFragment.email)
        }

        binding.saveBtn.setOnClickListener {
            if (binding.nameTxt.text.toString().isEmpty()) {
                binding.nameTxt.error = "Enter Name"
                binding.nameTxt.requestFocus()
                return@setOnClickListener
            }
            if (binding.emailTxt.text.toString().isEmpty()) {
                binding.emailTxt.error = "Enter Email"
                binding.emailTxt.requestFocus()
                return@setOnClickListener
            }
            if (binding.passwordTxt.text.toString().isEmpty()) {
                binding.passwordTxt.error = "Enter Password"
                binding.passwordTxt.requestFocus()
                return@setOnClickListener
            }
                val name = binding.nameTxt.text.toString().trim()
                val email = binding.emailTxt.text.toString().trim()
                val password = binding.passwordTxt.text.toString().trim()

                if (CustomerFragment.edit == "2") {
                    apiCallEditCustmor(name, email, password)
                } else {
                    apiCallCreateCustmor(name, email, password)

                }
            }
    }

    private fun apiCallCreateCustmor(name: String, email: String, password: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.createCustomer(sessionManager.authToken, name, email, password)
            .enqueue(object : Callback<ModelCreateCus> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateCus>, response: Response<ModelCreateCus>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 401) {
                            myToast(
                                requireContext() as Activity,
                                "Maximum Location Exceeded Please Update Your Plan"
                            )
                            AppProgressBar.hideLoaderDialog()
                        } else {
                            myToast(requireActivity(), response.body()!!.data)
                            binding.nameTxt.text.clear()
                            binding.emailTxt.text.clear()
                            binding.passwordTxt.text.clear()
                            AppProgressBar.hideLoaderDialog()
                            Navigation.findNavController(binding!!.root).navigate(R.id.customerFragment)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                    // apiCallGetPrePending1()

                    //  binding.progressBar.progress = 100

                }

                override fun onFailure(call: Call<ModelCreateCus>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
    private fun apiCallEditCustmor(name: String, email: String, password: String) {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.editCustomer(sessionManager.authToken,CustomerFragment.idNew , "yes",name, email, password)
            .enqueue(object : Callback<ModelCreateCus> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateCus>, response: Response<ModelCreateCus>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 401) {
                            myToast(
                                requireContext() as Activity,
                                "Maximum Location Exceeded Please Update Your Plan"
                            )
                            AppProgressBar.hideLoaderDialog()
                        } else {
                            myToast(requireActivity(), response.body()!!.data)
                            binding.nameTxt.text.clear()
                            binding.emailTxt.text.clear()
                            binding.passwordTxt.text.clear()
                            AppProgressBar.hideLoaderDialog()
                            CustomerFragment.edit="1"
                            Navigation.findNavController(binding!!.root).navigate(R.id.customerFragment)

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                    // apiCallGetPrePending1()

                    //  binding.progressBar.progress = 100

                }

                override fun onFailure(call: Call<ModelCreateCus>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }
    override fun onDestroy() {
        super.onDestroy()
        CustomerFragment.edit ="1"
    }
}