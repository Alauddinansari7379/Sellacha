package com.android.sellacha.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.android.sellacha.LogIn.LoginActivity
import com.android.sellacha.R
import com.android.sellacha.api.ApiResponse
import com.android.sellacha.api.response.LoginResponse
import com.android.sellacha.api.service.MainService
import com.android.sellacha.app.SellAchaApplication
import com.android.sellacha.databinding.ActivityLoginBinding
import com.android.sellacha.databinding.ActivityProfileBinding
import com.android.sellacha.dialog.AppDialog
import com.android.sellacha.dialog.AppDialog.WayremAlertDialogListener
import com.android.sellacha.helper.PreferenceManger
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.StatusBarUtils
import com.example.ehcf.sharedpreferences.SessionManager
import com.google.gson.JsonNull

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    var userDetails: LoginResponse? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)

        StatusBarUtils.transparentStatusAndNavigation(this)
        if (SellAchaApplication.getPreferenceManger().userDetails != null) {
            userDetails = SellAchaApplication.getPreferenceManger().userDetails
        }

        binding.email.text = sessionManager.email
        binding.name.text = sessionManager.customerName

        binding.backBtn.setOnClickListener { view: View? -> onBackPressed() }
        binding.logOUt.setOnClickListener { view: View? ->
            showAlertDialog(
                "Log Out",
                "Do you want to Logout ?",
                "Yes",
                "No",
                WayremAlertDialogListener { logOut() })
        }
    }

    fun logOut() {
        AppProgressBar.showLoaderDialog(this)
        MainService.logout(this).observe(this) { response: ApiResponse? ->
            if (response == null) {
                errorSnackBar(binding!!.root, getString(R.string.something_wrong))
            } else {
                if (response.data !is JsonNull) {
                    if (response.data != null) {
                        successSnackBar(binding!!.root, "Log Out Successfully")
                        SellAchaApplication.getPreferenceManger()
                            .putString(PreferenceManger.AUTH_TOKEN, "Bearer " + userDetails!!.token)
                        SellAchaApplication.getPreferenceManger().putUserDetails(null)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim)
                        finish()
                        finishAffinity()
                    } else {
                        showAlertDialog(
                            getString(R.string.app_name),
                            response.message,
                            "OK",
                            "",
                            { obj: AppDialog -> obj.dismiss() })
                    }
                } else {
                    errorSnackBar(binding!!.root, response.message)
                }
            }
            AppProgressBar.hideLoaderDialog()
        }
    }
}