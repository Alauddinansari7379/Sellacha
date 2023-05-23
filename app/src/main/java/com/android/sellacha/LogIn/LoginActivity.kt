package com.android.sellacha.LogIn

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.sellacha.LogIn.Model.ModelLogin
import com.android.sellacha.R
import com.android.sellacha.activity.BaseActivity
import com.android.sellacha.activity.HomeDashBoard
import com.android.sellacha.activity.StoreInformationActivity
import com.android.sellacha.app.SellAchaApplication
import com.android.sellacha.databinding.ActivityLoginBinding
import com.android.sellacha.helper.PreferenceManger
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.TextUtils
import com.android.sellacha.utils.Validations
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    private val context: Context = this@LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)

        if (sessionManager.isLogin) {
            startActivity(Intent(context, HomeDashBoard::class.java))
            finish()
        }


    binding.tvSignUp.setOnClickListener {
        startActivity(Intent(this@LoginActivity, StoreInformationActivity::class.java))
        overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim)
    }

      //  showHidePass(binding.root)
        binding.SignInBtn.setOnClickListener {
            if (binding.emailEdt.text!!.isEmpty()) {
                binding.emailEdt.error = "Email Email Number"
                errorSnackBar(binding.root, "Please Enter Your Email")
                binding.emailEdt.requestFocus()
                return@setOnClickListener
            }
            if (binding.passwordEdt.text!!.isEmpty()) {
                if (!Validations.validateEmail(binding.emailEdt.text.toString())) {
                    errorSnackBar(binding.root, "Please Enter Valid Email")
                    return@setOnClickListener
                }
                binding.passwordEdt.error = "Enter Password"
                errorSnackBar(binding.root, "Please Enter Your Password")
                binding.passwordEdt.requestFocus()
                return@setOnClickListener
            }
            apiCallLogin()

        }
    }
private fun apiCallLogin(){
    AppProgressBar.showLoaderDialog(this)

    val emailEdt = binding.emailEdt.text.toString().trim()
    val password = binding.passwordEdt.text.toString().trim()
    ApiClient.apiService.login(emailEdt,password).enqueue(object : Callback<ModelLogin> {
        @SuppressLint("LogNotTimber")
        override fun onResponse(
            call: Call<ModelLogin>, response: Response<ModelLogin>
        ) {
            if (response.code()==200) {
                // myToast(requireActivity(),"No Data Found")
                sessionManager.isLogin = true
                sessionManager.authToken = "Bearer "+response.body()!!.data.token
                sessionManager.email = response.body()!!.data.email
                sessionManager.customerName = response.body()!!.data.name
                sessionManager.baseURL ="https://footwear.thedemostore.in/"
                SellAchaApplication.getPreferenceManger().putString(PreferenceManger.AUTH_TOKEN, "Bearer " +response.body()!!.data.token)
                Log.e("email",sessionManager.email.toString())
                Log.e("customerName",sessionManager.customerName.toString())
                Log.e("authToken",sessionManager.authToken.toString())

                val intent = Intent(applicationContext, HomeDashBoard::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                finish()
                startActivity(intent)
                AppProgressBar.hideLoaderDialog()

            } else {
                successSnackBar(binding.root, response.message())
                AppProgressBar.hideLoaderDialog()

            }
        }

        override fun onFailure(call: Call<ModelLogin>, t: Throwable) {
            errorSnackBar(binding.root,"Something went wrong")
            AppProgressBar.hideLoaderDialog()


        }

    })

}

//    fun openLink(str: String?, text: TextView?) {
//        val link: Link = Link(str!!)
//            .setTextColor(Color.parseColor("#0191B5"))
//            .setHighlightAlpha(.4f)
//            .setUnderlined(false)
//            .setBold(true)
//            .setOnClickListener(Link.OnClickListener { clickedText: String? ->
//                startActivity(Intent(this@LoginActivity, StoreInformationActivity::class.java))
//                overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim)
//            })
//        if (text != null) {
//            LinkBuilder.on(text)
//                .addLink(link)
//                .build()
//        }
//    }

//    fun loginApiCaller(email: String?, password: String?) {
//        AppProgressBar.showLoaderDialog(this)
//        val model = LoginRequest()
//        model.setEmail(email)
//        model.setPassword(password)
//        MainService.userLogin(this, model)
//            .observe(this, Observer<ApiResponse> { responseLogin: ApiResponse? ->
//                if (responseLogin == null) {
//                    errorSnackBar(binding.getRoot(), getString(R.string.something_wrong))
//                } else {
//                    if (responseLogin.getData() !is JsonNull) {
//                        if (responseLogin.getData() != null) {
//                            successSnackBar(binding.getRoot(), "Login Successfully")
//                            val userDetails: LoginResponse = Gson().fromJson<LoginResponse>(
//                                responseLogin.getData(),
//                                LoginResponse::class.java
//                            )
//                            SellAchaApplication.getPreferenceManger().putString(
//                                PreferenceManger.AUTH_TOKEN,
//                                "Bearer " + userDetails.getToken()
//                            )
//                            SellAchaApplication.getPreferenceManger().putUserDetails(userDetails)
//                            startActivity(Intent(this, HomeDashBoard::class.java))
//                            overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim)
//                            finish()
//                        } else {
//                            showAlertDialog(
//                                getString(R.string.app_name),
//                                responseLogin.getMessage(),
//                                "OK",
//                                "",
//                                WayremAlertDialogListener { obj: AppDialog -> obj.dismiss() })
//                        }
//                    } else {
//                        errorSnackBar(binding.getRoot(), responseLogin.getMessage())
//                    }
//                }
//                AppProgressBar.hideLoaderDialog()
//            })
//    }

    fun checkValidation(): Boolean {
        if (TextUtils.isEmpty(binding.emailEdt.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Your Email")
            return false
        }
        if (!Validations.validateEmail(binding.emailEdt.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Valid Email")
            return false
        }
        if (TextUtils.isEmpty(binding.passwordEdt.getText().toString())) {
            errorSnackBar(binding.getRoot(), "Please Enter Your Password")
            return false
        }
        return true
    }
//
//    fun showHidePass(view: View) {
//        if (binding.passwordEdt.getTransformationMethod()
//                .equals(PasswordTransformationMethod.getInstance())
//        ) {
//            (view as ImageView).setImageResource(R.drawable.ic_baseline_visibility_24)
//            //   ((ImageView) (view)).setColorFilter(ContextCompat.getColor(this, R.color.light_primary_bg));
//            binding.passwordEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
//            binding.passwordEdt.setSelection(binding.passwordEdt.getText().length)
//        } else {
//            (view as ImageView).setImageResource(R.drawable.ic_baseline_visibility_off_24)
//            //   ((ImageView) (view)).setColorFilter(ContextCompat.getColor(this, R.color.light_primary_bg));
//            binding.passwordEdt.setTransformationMethod(PasswordTransformationMethod.getInstance())
//            binding.passwordEdt.setSelection(binding.passwordEdt.getText().length)
//        }
//    }
}