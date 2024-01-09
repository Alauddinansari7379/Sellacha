package com.android.sellacha.LogIn

import android.os.Bundle
import android.util.Patterns
import androidx.databinding.DataBindingUtil
import com.android.sellacha.Order.Model.ModelCoupon
import com.android.sellacha.R
import com.android.sellacha.activity.BaseActivity
 import com.android.sellacha.databinding.ActivityForgotPasswordBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.StatusBarUtils
import com.android.sellacha.utils.TextUtils
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword : BaseActivity() {
    var binding: ActivityForgotPasswordBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        StatusBarUtils.statusBarColor(this, R.color.primary_bg)



        with(binding!!) {
            backBtn.setOnClickListener {
                onBackPressed()
            }
            submit.setOnClickListener {
                if (oldAddresLb.text.toString().isEmpty()) {
                    oldAddresLb.error = "Enter Email Address"
                    oldAddresLb.requestFocus()
                    return@setOnClickListener
                }
                if (Patterns.EMAIL_ADDRESS.matcher(oldAddresLb.text.toString().trim()).matches()) {
                    apiCallForgotPass()
                } else {
                    oldAddresLb.error = "Please Enter a valid email"
                    oldAddresLb.requestFocus()
                    return@setOnClickListener
                }

            }

        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun apiCallForgotPass() {
        AppProgressBar.showLoaderDialog(this@ForgotPassword)
        ApiClient.apiService.forgotPass(binding!!.oldAddresLb.text.toString().trim())
            .enqueue(object : Callback<ModelCoupon> {
                override fun onResponse(call: Call<ModelCoupon>, response: Response<ModelCoupon>) {
                    try {
                        if (response.code() == 500) {
                            myToast(this@ForgotPassword, "Server Error")
                            AppProgressBar.hideLoaderDialog()
                        } else if (response.code() == 404) {
                            myToast(this@ForgotPassword, "Something went wrong")
                            AppProgressBar.hideLoaderDialog()
                        } else {
                            myToast(this@ForgotPassword, response.body()!!.data)
                            AppProgressBar.hideLoaderDialog()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        myToast(this@ForgotPassword, "Something went Wrong")
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                   // apiCallForgotPass()
                    myToast(this@ForgotPassword, "Something went Wrong")
                    AppProgressBar.hideLoaderDialog()

                }

            })
    }

}