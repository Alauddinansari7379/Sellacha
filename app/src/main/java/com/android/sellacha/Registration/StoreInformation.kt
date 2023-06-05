package com.android.sellacha.Registration

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.Registration.Model.ModelRegistration
import com.android.sellacha.activity.HomeDashBoard
import com.android.sellacha.databinding.FragmentStoreInformationBinding
import com.android.sellacha.fragment.BaseFragment
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class StoreInformation : BaseFragment(), UploadRequestBody.UploadCallback {
    var binding: FragmentStoreInformationBinding? = null
    private lateinit var sessionManager: SessionManager
    var bussness = ""
    private var logo: Uri? = null

    var productType = ""
    var serviceType = ""
    var productList = ArrayList<ModelProductType>()
    var serviceList = ArrayList<ModelProductType>()
    var serviceList1 = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_store_information, container, false)


        // Inflate the layout for this fragmentre
        sessionManager = SessionManager(requireContext())

        binding!!.storeInfoLb.setOnClickListener {
            //apiCallRegistration()
        }
        binding!!.txtproductTxt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                if (binding!!.txtproductTxt.length() >= 8) {
                    binding!!.txtproductTxt.setTextColor(Color.parseColor("#FF4CAF50"));
                    binding!!.txtPasswordCon.setTextColor(Color.parseColor("#FF4CAF50"));

                    // binding!!.txtproductTxt.setBackgroundResource(R.drawable.corner_green);

                } else {
                    binding!!.txtproductTxt.setTextColor(Color.parseColor("#FFF44336"));

                  //  binding!!.txtproductTxt.setBackgroundResource(R.drawable.corner_red);


                }
            }
        })
        binding!!.saveBtn.setOnClickListener {
            if (binding!!.txtBusinessName.text.isEmpty()) {
                binding!!.txtBusinessName.error = "Enter BusinessName"
                binding!!.txtBusinessName.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.txtMobNo.text.isEmpty()) {
                binding!!.txtMobNo.error = "Enter Mobile Number"
                binding!!.txtMobNo.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.txtEmail.text.isEmpty()) {
                binding!!.txtEmail.error = "Enter Email"
                binding!!.txtEmail.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.txtproductTxt.text.isEmpty()) {
                binding!!.txtproductTxt.error = "Enter Password"
                binding!!.txtproductTxt.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.txtPasswordCon.text.isEmpty()) {
                binding!!.txtPasswordCon.error = "Enter Confirm Password"
                binding!!.txtPasswordCon.requestFocus()
                return@setOnClickListener
            }
            val password = binding!!.txtproductTxt.text.toString().trim()
            val confirmPassword = binding!!.txtPasswordCon.text.toString().trim()

            if (password.length != 8) {
                binding!!.txtproductTxt.error = "Enter 8 Digit Password"
                binding!!.txtproductTxt.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding!!.txtPasswordCon.error = "Password Miss Match"
                binding!!.txtPasswordCon.requestFocus()
                return@setOnClickListener
            }

            RegistrationData.business_name = binding!!.txtBusinessName.text.toString().trim()
            RegistrationData.mob = binding!!.txtMobNo.text.toString()
            RegistrationData.type = productType
            RegistrationData.refrral = binding!!.txtReferralNo.text.toString()
            RegistrationData.email = binding!!.txtEmail.text.toString()
            RegistrationData.password = binding!!.txtPasswordCon.text.toString()

            findNavController(binding!!.root).navigate(R.id.themeFragment)
        }

        productList.add(ModelProductType("Product", 0))
        productList.add(ModelProductType("Service", 1))
        productList.add(ModelProductType("Both", 2))

        binding!!.txtPassword.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            productList
        )

        serviceList1.add("New")
        serviceList1.add("New1")

        serviceList.add(ModelProductType("Clothes", 0))
        serviceList.add(ModelProductType("Shoes", 1))
        binding!!.txtselectshoptp.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            serviceList
        )
        binding!!.businessnameLb.setOnClickListener {
            opeinImageChooser()
        }

        binding!!.txtPassword.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    if (productList.size > 0) {
                        RegistrationData.utype = productList[i].text.toString()

                        Log.e(ContentValues.TAG, "productType: $productType")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

        binding!!.txtselectshoptp.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    if (serviceList.size > 0) {
                        RegistrationData.sertype = serviceList[i].text.toString()

                        Log.e(ContentValues.TAG, "serviceType: $serviceType")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        return binding!!.root
    }


//    private fun  convertImageToBase64(filePath: String): String {
//        val file = File(filePath)
//        val inputStream = FileInputStream(file)
//        val bytes = ByteArray(file.length().toInt())
//
//        try {
//            inputStream.read(bytes)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            inputStream.close()
//        }
//        return Base64.encodeToString(bytes, Base64.DEFAULT)
//    }
//
//    val imagePath = "/path/to/image.jpg" // Replace with the actual file path
//    val base64Image = convertImageToBase64(imagePath)

    class RegistrationData {
        companion object {
            var name = ""
            var email = ""
            var password = ""
            var domain = ""
            var custom_domain = ""
            var utype = ""
            var sertype = ""
            var refrral = ""
            var mob = ""
            var business_name = ""
            const val shop_type = ""
             var logo: Uri? = null
            var favicon: Uri? = null
             var thumbnail: Uri? = null
            var theme_color = ""
            var url = ArrayList<String>()
            var icon = ArrayList<String>()

//            var url = ""
//            var icon = ""
            var cname = ""
            const val p_id = ""
            var file = ""
            var featured = ""
            var menu_status = ""
            var title = ""
            var special_price_start = ""
            var special_price = ""
            var price_type = ""
            var price = ""
            var type = ""
            var special_price_end = ""
            var ga_measurement_id = ""
            var analytics_view_id = ""
            var astatus = ""
            const val gfile = ""
            var tag_id = ""
            var tstatus = ""
            var pixel_id = ""
            var pstatus = ""
            var number = ""
            var shop_page_pretext = ""
            var other_page_pretext = ""
            var wstatus = ""

        }
    }

/*
    private fun apiCallRegistration() {

        AppProgressBar.showLoaderDialog(requireContext())

//        if (logo == null) {
//            myToast(requireActivity(), "Select Logo")
//            return
//        }
//        if (favicon == null) {
//            myToast(requireActivity(), "Select Favicon")
//            return
//        }
//
//        if (thumbnail == null) {
//            myToast(requireActivity(), "Select Favicon")
//            return
//        }
//        val parcelFileDescriptorLogo =
//            activity?.contentResolver?.openFileDescriptor(logo!!, "r", null)
//                ?: return

//        val parcelFileDescriptorFavicon =
//            activity?.contentResolver?.openFileDescriptor(favicon!!, "r", null)
//                ?: return
//        val parcelFileDescriptorThumbnail =
//            activity?.contentResolver?.openFileDescriptor(favicon!!, "r", null)
//                ?: return

//        val inputStreamLogo = FileInputStream(parcelFileDescriptorLogo.fileDescriptor)
//        var logo = File(requireActivity().cacheDir, activity?.contentResolver!!.getFileName(logo!!))

//        val inputStreamFavicon = FileInputStream(parcelFileDescriptorFavicon.fileDescriptor)
//        var logoFavicon =
//            File(requireActivity().cacheDir, activity?.contentResolver!!.getFileName(favicon!!))
//
//        val inputStreamThumbnail = FileInputStream(parcelFileDescriptorThumbnail.fileDescriptor)
//        var thumbnail =
//            File(requireActivity().cacheDir, activity?.contentResolver!!.getFileName(thumbnail!!))

//        val outputStreamLogo = FileOutputStream(logo)
//        inputStreamLogo.copyTo(outputStreamLogo)

//
//        val outputStreamFavicon = FileOutputStream(logoFavicon)
//        inputStreamFavicon.copyTo(outputStreamFavicon)
//
//        val outputStreamThumbnail = FileOutputStream(thumbnail)
//        inputStreamThumbnail.copyTo(outputStreamThumbnail)


        //  StoreInformation.RegistrationData.logo = logo.toString()
        //  StoreInformation.RegistrationData.favicon = logoFavicon.toString()


        //   val bodyLogo = UploadRequestBody(logo, "image", this)
//        val bodyFavicon = UploadRequestBody(logoFavicon, "image", this)
//        val bodyThumbnail = UploadRequestBody(thumbnail, "image", this)

        //    MultipartBody.Part.createFormData("image", logo.name, bodyLogo)
//        MultipartBody.Part.createFormData("image", logoFavicon.name, bodyFavicon)
//        MultipartBody.Part.createFormData("image", thumbnail.name, bodyThumbnail)

      //  "json".toRequestBody("multipart/form-data".toMediaTypeOrNull())



            ApiClient.apiService.registration(
                "new cxname",
                "alsds123123@wqgmail.com",
                "12345678",
                "newAlaudddsdsin12eu",
                "abddd.c0m",
                "product",
                "Clothes",
                "",
                "65425001254",
                "mubunxcessnes",
                "1",
                "",
                "",
                "",
                "serviceList1",
                "cname",
                "pid",
                "",
                "yes",
                "yes",
                "tile",
                "1000",
                "3000",
                "pricetype",
                "5000",
                "product",
                "1000",
                "ABCd1234",
                "Abcd1234",
                "yes",
                "",
                "Abcd12344",
                "yes",
                "Abcd12344",
                "yes",
                "1233423424",
                "sdjhfbsjhfbdsf",
                "dfjsjfhsbdf",
                "yes",
            )


                .enqueue(object : Callback<ModelRegistration> {
                    @SuppressLint("LogNotTimber")
                    override fun onResponse(
                        call: Call<ModelRegistration>, response: Response<ModelRegistration>
                    ) {
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()


                        } else if (response.code() == 401) {
                            myToast(requireActivity(), response.body()!!.data.msg)
                            AppProgressBar.hideLoaderDialog()

                        }
                        else if (response.code() == 404) {
                            myToast(requireActivity(), response.body()!!.data.msg)
                            AppProgressBar.hideLoaderDialog()

                        }
                        else if (response.body()!!.message=="422") {
                            myToast(requireActivity(), response.body()!!.data.errors)
                            AppProgressBar.hideLoaderDialog()

                        }

                        else if (response.body()!!.success.toString() == "false") {
                            myToast(requireActivity(), response.body()!!.message)
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.body()!!.success.toString() == "true") {
                            startActivity(Intent(context, HomeDashBoard::class.java))
                            activity!!.overridePendingTransition(
                                R.anim.bottom_anim,
                                R.anim.bottom_out_anim
                            )
                            AppProgressBar.hideLoaderDialog()
                            sessionManager.isLogin = true

                        } else {
                            myToast(requireActivity(), response.body()!!.data.msg)
                        }

                    }

                    override fun onFailure(call: Call<ModelRegistration>, t: Throwable) {
                        myToast(requireActivity(), "Something went wrong")
                        AppProgressBar.hideLoaderDialog()


                    }

                })

    }
*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ColorSchemeFragment.REQUEST_CODE_IMAGE -> {

                    logo = data?.data
//                            binding!!.logotxtNoFile.text = "Logo Selected"
//                            binding!!.logotxtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));


                    Log.e("data?.data", data?.data.toString())
                }
            }
        }
    }

    private fun opeinImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            (MediaStore.ACTION_IMAGE_CAPTURE)
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, ColorSchemeFragment.REQUEST_CODE_IMAGE)

//        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
//        pdfIntent.type = "application/pdf"
//        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
//        startActivityForResult(pdfIntent, REQUEST_CODE_IMAGE)
//
        }
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

    override fun onProgressUpdate(percentage: Int) {
        TODO("Not yet implemented")
    }

}
