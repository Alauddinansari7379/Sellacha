package com.android.sellacha.Profile.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.sellacha.Costomer.adapter.AdapterCustomer
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.Profile.model.ModelUserDetial
import com.android.sellacha.databinding.ActivityUserDetailsBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.StatusBarUtils
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetails : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtils.transparentStatusAndNavigation(this)
        sessionManager = SessionManager(this)
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        apiCallCustomerDetails()

    }

    private fun apiCallCustomerDetails() {
        AppProgressBar.showLoaderDialog(this@UserDetails)

        ApiClient.apiService.me(sessionManager.authToken)
            .enqueue(object : Callback<ModelUserDetial> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelUserDetial>, response: Response<ModelUserDetial>
                ) {
                    if (response.code() == 500) {
                        myToast(this@UserDetails, "Server Error")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.body()!!.success) {
                        binding.edtFirstName.text = response.body()!!.data.first_name.toString()
                        binding.edtLastName.text = response.body()!!.data.last_name.toString()
                        binding.edtName.text = response.body()!!.data.name
                        binding.edtEmail.text = response.body()!!.data.email
                        binding.edtMobileNumber.text = response.body()!!.data.mob
                        binding.edtCity.text = response.body()!!.data.city.toString()
                        AppProgressBar.hideLoaderDialog()


                    } else {
                        myToast(this@UserDetails, "Something went wrong")

                        AppProgressBar.hideLoaderDialog()


                    }
                }

                override fun onFailure(call: Call<ModelUserDetial>, t: Throwable) {
                   // myToast(this@UserDetails, "Something went wrong")
                    apiCallCustomerDetails()

                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}