package com.android.sellacha.Profile.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.sellacha.Profile.model.ModelChangePass
import com.android.sellacha.databinding.ActivityChangePasswordBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassword : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)


        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.btnChangePassword.setOnClickListener {

            if (binding.oldAddresLb.text.toString().isEmpty()) {
                binding.oldAddresLb.error = "Enter Old Password"
                binding.oldAddresLb.requestFocus()
                return@setOnClickListener
            }
            if (binding.newPasswordLb.text.toString().isEmpty()) {
                binding.newPasswordLb.error = "Enter New Password"
                binding.newPasswordLb.requestFocus()
                return@setOnClickListener
            }
            if (binding.customerTypeLb.text.toString().isEmpty()) {
                binding.customerTypeLb.error = "Reenter Password"
                binding.customerTypeLb.requestFocus()
                return@setOnClickListener
            }
            val oldPass = binding.oldAddresLb.text.toString()
            val newPass = binding.newPasswordLb.text.toString()
            val againPass = binding.customerTypeLb.text.toString()
            if (newPass != againPass) {
                binding.customerTypeLb.error = "Password Miss Match"
                binding.customerTypeLb.requestFocus()
                return@setOnClickListener
            } else {
                apiCallChangePassword(oldPass, againPass)
            }

        }
    }


    private fun apiCallChangePassword(oldPass: String, againPass: String) {
        AppProgressBar.showLoaderDialog(this@ChangePassword)
        Log.e("Old", oldPass)
        Log.e("againPass", againPass)
        ApiClient.apiService.userProfileUpdatePass(sessionManager.authToken, oldPass, againPass)
            .enqueue(object : Callback<ModelChangePass> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelChangePass>, response: Response<ModelChangePass>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(this@ChangePassword, "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.body()!!.success) {
                            myToast(this@ChangePassword, response.body()!!.data)
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            myToast(this@ChangePassword, response.body()!!.data)
                            AppProgressBar.hideLoaderDialog()


                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        myToast(this@ChangePassword, "Something went wrong")
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelChangePass>, t: Throwable) {
                    myToast(this@ChangePassword, "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

}