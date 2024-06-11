package com.android.sellacha.Shipping.Nimbus

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.R
import com.android.sellacha.Shipping.Nimbus.Model.ModelLoginNimbus
import com.android.sellacha.Shipping.Nimbus.Model.ModelNimbusToken
import com.android.sellacha.databinding.FragmentNimbusBinding
import com.android.sellacha.helper.myToast
 import com.android.sellacha.sharedpreferences.SessionManager
import com.android.sellacha.utils.AppProgressBar
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NimbusFragment : Fragment() {
    lateinit var binding: FragmentNimbusBinding
    private lateinit var sessionManager: SessionManager
    var count2=0
    var count=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nimbus, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNimbusBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        binding.signUp.setOnClickListener {
            val browse = Intent(Intent.ACTION_VIEW, Uri.parse("https://ship.nimbuspost.com/users/signup"))
            startActivity(browse)
        }

        binding.btnAuthorizeNow.setOnClickListener {
            if (binding.email.text!!.isEmpty()){
                binding.email.error="Enter Email"
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (binding.Password.text!!.isEmpty()){
                binding.Password.error="Enter Password"
                binding.Password.requestFocus()
                return@setOnClickListener
            }
            if (isValidEmail(binding.email.text.toString().trim())){
                apiCalLoginNimbus()
            }else{
                binding.email.error="Enter Valid Email"
                binding.email.requestFocus()
                return@setOnClickListener
            }

        }
        apiCallGetToken()


    }

    private fun apiCallGetToken() {
        AppProgressBar.showLoaderDialog(activity)


        ApiClient.apiService.getNimbusToken(
            sessionManager.authToken.toString()
        )
            .enqueue(object : Callback<ModelNimbusToken> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelNimbusToken>, response: Response<ModelNimbusToken>
                ) {
                    try {
                        if (response.code() == 500) {
                            activity?.let { myToast(it, "Server Error") }
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            activity?.let { myToast(it, "Something went wrong") }
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            binding.edtToken.setText(response.body()!!.data.token.toString())
                            AppProgressBar.hideLoaderDialog()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        activity?.let { myToast(it, "Something went wrong") }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelNimbusToken>, t: Throwable) {
                    count2++
                    if (count2 <= 3) {
                        Log.e("count", count2.toString())
                        apiCallGetToken()
                    } else {
                        activity?.let { myToast(it, t.message.toString()) }
                        AppProgressBar.hideLoaderDialog()

                    }
                    AppProgressBar.hideLoaderDialog()
                }


            })
    }
    private fun apiCalLoginNimbus() {
        AppProgressBar.showLoaderDialog(activity)


        ApiClient.apiService.loginNimbus(
            sessionManager.authToken.toString(),binding.email.text.toString().trim(),binding.Password.text.toString().trim()
        )
            .enqueue(object : Callback<ModelLoginNimbus> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelLoginNimbus>, response: Response<ModelLoginNimbus>
                ) {
                    try {
                        if (response.code() == 500) {
                            activity?.let { myToast(it, "Server Error") }
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            activity?.let { myToast(it, "Something went wrong") }
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            //Need To Change Model
                            activity?.let { myToast(it, response.body()!!.data.toString()) }
                            AppProgressBar.hideLoaderDialog()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        activity?.let { myToast(it, "Something went wrong") }
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelLoginNimbus>, t: Throwable) {
                    count++
                    if (count <= 3) {
                        Log.e("count", count.toString())
                        apiCalLoginNimbus()
                    } else {
                        activity?.let { myToast(it, t.message.toString()) }
                        AppProgressBar.hideLoaderDialog()

                    }
                    AppProgressBar.hideLoaderDialog()
                }


            })
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }
}
