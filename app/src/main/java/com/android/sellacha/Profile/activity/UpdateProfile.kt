package com.android.sellacha.Profile.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.sellacha.Order.Model.ModelCoupon
import com.android.sellacha.Profile.activity.ProfileActivity.Companion.value
import com.android.sellacha.R
import com.android.sellacha.activity.HomeDashBoard
import com.android.sellacha.databinding.ActivityUpdateProfileBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfile : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this@UpdateProfile)

        with(binding) {
            backBtn.setOnClickListener {
                onBackPressed()

            }
            btnUpdate.setOnClickListener {
                if (edtName.text.toString().isEmpty()) {
                    edtName.error = "Enter Name"
                    edtName.requestFocus()
                    return@setOnClickListener
                }
                if (edtEmail.text.toString().isEmpty()) {
                    edtEmail.error = "Enter Email"
                    edtEmail.requestFocus()
                    return@setOnClickListener
                }
                if (edtMobileNumber.text.toString().isEmpty()) {
                    edtMobileNumber.error = "Enter Mobile Number"
                    edtMobileNumber.requestFocus()
                return@setOnClickListener
            }
                apiUpdateProfile()
            }
        }
    }

    private fun apiUpdateProfile() {
        AppProgressBar.showLoaderDialog(this@UpdateProfile)
        ApiClient.apiService.userProfileUpdate(
            sessionManager.authToken,
            binding!!.edtName.text.toString(),
            binding!!.edtEmail.text.toString(),
            binding!!.edtMobileNumber.text.toString(),
        )
            .enqueue(object : Callback<ModelCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCoupon>, response: Response<ModelCoupon>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(this@UpdateProfile, "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            myToast(this@UpdateProfile, "Something Wnt Wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            value="2"
                             myToast(this@UpdateProfile, response.body()!!.data)
                             sessionManager.customerName=binding.edtName.text.toString()
                            sessionManager.email=binding.edtEmail.text.toString()
                            sessionManager.phoneNumber=binding.edtMobileNumber.text.toString()
                             AppProgressBar.hideLoaderDialog()
                            startActivity(Intent(this@UpdateProfile,ProfileActivity::class.java))
                           // refresh()
                        }
                    }catch (e:Exception){
                        myToast(this@UpdateProfile, "Something Went Wrong")

                    }
                }

                override fun onFailure(call: Call<ModelCoupon>, t: Throwable) {
                    apiUpdateProfile()
                     AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun refresh(){
         overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}