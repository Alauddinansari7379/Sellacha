package com.android.sellacha.Profile.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import com.android.sellacha.Home.model.ModelOrderCount
import com.android.sellacha.LogIn.LoginActivity
import com.android.sellacha.Profile.model.ModelLogo
import com.android.sellacha.Profile.model.ModelUpdateProfile
import com.android.sellacha.R
import com.android.sellacha.activity.BaseActivity
import com.android.sellacha.activity.HomeDashBoard
import com.android.sellacha.api.ApiResponse
import com.android.sellacha.api.response.LoginResponse
import com.android.sellacha.api.service.MainService
import com.android.sellacha.app.SellAchaApplication
import com.android.sellacha.databinding.ActivityProfileBinding
import com.android.sellacha.dialog.AppDialog
import com.android.sellacha.dialog.AppDialog.WayremAlertDialogListener
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.StatusBarUtils
import com.bumptech.glide.Glide
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonNull
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ProfileActivity : BaseActivity(), UploadRequestBody.UploadCallback {
    private lateinit var binding: ActivityProfileBinding
    var userDetails: LoginResponse? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)

        StatusBarUtils.transparentStatusAndNavigation(this)

        if (SellAchaApplication.getPreferenceManger().userDetails != null) {
            userDetails = SellAchaApplication.getPreferenceManger().userDetails
        }

        binding.layoutProfileSetting.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, UpdateProfile::class.java))
        }

        binding.email.text = sessionManager.email
        binding.name.text = sessionManager.customerName

        Log.e("tah", sessionManager.email.toString())
        Log.e("tah", sessionManager.customerName.toString())

        if (sessionManager.profilePic!!.isNotEmpty()) {
            Picasso.get().load("${sessionManager.profilePic}").into(binding.userProfile)
            Log.e("pofile", "${sessionManager.profilePic}")
        }

        binding.layoutUerDetails.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, UserDetails::class.java))
        }
        binding.layoutPassChange.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, ChangePassword::class.java))
        }

        binding.btnSave.setOnClickListener {
            apiCallUpdateProfilePic()
        }

        binding.cameraBtn.setOnClickListener {
            opeinImageChooserNew()
        }


        binding.backBtn.setOnClickListener { view: View? ->
            onBackPressed()
            if (value == "2") {
                value = "1"
                startActivity(Intent(this@ProfileActivity, HomeDashBoard::class.java))
            }
        }
        binding.logOUt.setOnClickListener { view: View? ->
            showAlertDialog(
                "Log Out",
                "Do you want to Logout ?",
                "Yes",
                "No",
                WayremAlertDialogListener { logOut() })
        }
    }

    private fun logOut() {
        AppProgressBar.showLoaderDialog(this)
        MainService.logout(this).observe(this) { response: ApiResponse? ->
            if (response == null) {
                errorSnackBar(binding!!.root, getString(R.string.something_wrong))
            } else {
                if (response.data !is JsonNull) {
                    if (response.data != null) {
                        successSnackBar(binding!!.root, "Log Out Successfully")
//                        SellAchaApplication.getPreferenceManger()
//                            .putString(PreferenceManger.AUTH_TOKEN, "Bearer " + userDetails!!.token)
//                        SellAchaApplication.getPreferenceManger().putUserDetails(null)
                        sessionManager.logout()
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
                            ""
                        ) { obj: AppDialog -> obj.dismiss() }
                    }
                } else {
                    errorSnackBar(binding!!.root, response.message)
                }
            }
            AppProgressBar.hideLoaderDialog()
        }
    }

    private fun apiCallGetLogo() {

        // AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.getLogo(sessionManager.authToken)
            .enqueue(object : Callback<ModelLogo> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelLogo>, response: Response<ModelLogo>
                ) {
                    try {
                        if (response.code() == 404) {
                            myToast(this@ProfileActivity, "Server Error")
                            //   AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 500) {
                            myToast(this@ProfileActivity, "Server Error")
                            //   AppProgressBar.hideLoaderDialog()

                        } else {

                          //  sessionManager.profilePic = response.body()!!.data.logo
                            Picasso.get().load("${response.body()!!.data.logo}")
                                .into(binding.userProfile)
                            Log.e("URL",response.body()!!.data.logo)
                            Log.e("URLs",sessionManager.profilePic.toString())
                            AppProgressBar.hideLoaderDialog()
                          //  refresh()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }


                override fun onFailure(call: Call<ModelLogo>, t: Throwable) {
                    //  myToast(this@HomeFragment, t.message.toString())
                    AppProgressBar.hideLoaderDialog()

                }

            })
    }


    private fun apiCallUpdateProfilePic() {
        AppProgressBar.showLoaderDialog(this)


        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)

        val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)


        //  binding.progressBar.progress = 0
        val body = UploadRequestBody(file, "image", this)
        ApiClient.apiService.updateProfilePic(
            sessionManager.authToken.toString(),
            MultipartBody.Part.createFormData("reg_cer", file.name, body),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "json")
        )
            .enqueue(object : Callback<ModelUpdateProfile> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelUpdateProfile>, response: Response<ModelUpdateProfile>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(this@ProfileActivity, "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            myToast(this@ProfileActivity, "Something went wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            myToast(this@ProfileActivity, "${response.body()!!.data}")
                            AppProgressBar.hideLoaderDialog()
                            apiCallGetLogo()


                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        myToast(this@ProfileActivity, "Something went wrong")
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelUpdateProfile>, t: Throwable) {
                    apiCallUpdateProfilePic()
                  //  myToast(this@ProfileActivity, "Something went wrong")
                    AppProgressBar.hideLoaderDialog()

                }

            })
    }

    private fun opeinImageChooserNew() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            (MediaStore.ACTION_IMAGE_CAPTURE)
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE)

//            val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
//            pdfIntent.type = "application/pdf"
//            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
//            startActivityForResult(pdfIntent, REQUEST_CODE_IMAGE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE -> {
                    selectedImageUri = data?.data
                    binding.userProfile.setImageURI(selectedImageUri)
                    binding.btnSave.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        // binding.progressBar.progress = percentage
    }

    private fun ContentResolver.getFileName(selectedImageUri: Uri): String {
        var name = ""
        val returnCursor = this.query(selectedImageUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()


        }

        return name
    }

    private fun View.snackbar(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
            snackbar.setAction("OK") {
                snackbar.dismiss()
            }
        }.show()

    }


    companion object {
        var value = "1"
        const val REQUEST_CODE_IMAGE = 101

    }

    fun refresh() {
        overridePendingTransition(0, 0)
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

//    override fun onBackPressed() {
//        value = "1"
//        startActivity(Intent(this@ProfileActivity, HomeDashBoard::class.java))
//    }

}