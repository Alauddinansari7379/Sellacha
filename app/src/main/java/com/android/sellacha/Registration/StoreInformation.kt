package com.android.sellacha.Registration

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
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.analytics_view_id
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.astatus
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.astatusValue
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.business_name
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.cname
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.cnameValue
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.custom_domain
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.domain
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.email
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.favicon
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.featured
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.featuredValue
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.file
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.ga_measurement_id
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.icon
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.logo
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.menu_status
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.menu_statusValue
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.mob
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.name
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.number
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.other_page_pretext
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.p_id
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.password
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.pixel_id
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.price
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.price_type
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.price_typeValue
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.productTitle
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.pstatus
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.pstatusValue
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.refrral
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.sertype
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.shop_page_pretext
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.special_price
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.special_price_end
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.special_price_start
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.tag_id
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.tag_idName
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.theme_color
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.thumbnail
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.title
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.tstatus
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.tstatusValue
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.type
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.url
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.utype
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.utypeValue
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.wstatus
import com.android.sellacha.Registration.StoreInformation.RegistrationData.Companion.wstatusValue
import com.android.sellacha.databinding.FragmentStoreInformationBinding
import com.android.sellacha.fragment.BaseFragment
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.example.ehcf.sharedpreferences.SessionManager
import kotlin.collections.ArrayList

class StoreInformation : BaseFragment(), UploadRequestBody.UploadCallback {
    var binding: FragmentStoreInformationBinding? = null
    private lateinit var sessionManager: SessionManager
    var bussness = ""

    var productType = ""
    var serviceType = ""
    var productList = ArrayList<ModelProductType>()
    var serviceList = ArrayList<ModelProductType>()
    var serviceList1 = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_store_information, container, false)


        // Inflate the layout for this fragmentre
        sessionManager = SessionManager(requireContext())

        binding!!.storeInfoLb.setOnClickListener {
            //apiCallRegistration()
        }

        binding!!.txtBusinessName.setText(RegistrationData.business_name)
        binding!!.txtMobNo.setText(RegistrationData.mob)
        binding!!.txtPassword.setSelection(RegistrationData.utypeValue)
        binding!!.txtReferralNo.setText(RegistrationData.refrral)
        binding!!.txtEmail.setText(RegistrationData.email)
        binding!!.txtPasswordCon.setText(RegistrationData.password)
        binding!!.txtproductTxt.setText(RegistrationData.password)


        // binding.spinnerState.setSelection(items.indexOf(sessionManager.state.toString()));


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
            if (binding!!.txtMobNo.text.toString().length < 10) {
                binding!!.txtMobNo.error = "Enter Valid Mobile Number"
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

            if (password.length < 8) {
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
            RegistrationData.refrral = binding!!.txtReferralNo.text.toString()
            RegistrationData.email = binding!!.txtEmail.text.toString()
            RegistrationData.password = binding!!.txtPasswordCon.text.toString()

            findNavController(binding!!.root).navigate(R.id.themeFragment)
        }

        productList.add(ModelProductType("product", 0))
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
        serviceList.add(ModelProductType("Dairy", 1))
        serviceList.add(ModelProductType("Health", 1))
        serviceList.add(ModelProductType("Fruit", 1))
        serviceList.add(ModelProductType("Vegetable", 1))
        serviceList.add(ModelProductType("Clothing", 1))
        serviceList.add(ModelProductType("Hand Bags", 1))
        serviceList.add(ModelProductType("Hijab Wear", 1))
        serviceList.add(ModelProductType("Purses", 1))
        serviceList.add(ModelProductType("Shoes", 1))
        serviceList.add(ModelProductType("Cosmetics", 1))
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
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (productList.size > 0) {
                        RegistrationData.type= productList[i].text.toString()
                        RegistrationData.utype = productList[i].text.toString()
                        RegistrationData.utypeValue = productList[i].value

                        Log.e(ContentValues.TAG, "productType: $productType")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        binding!!.txtselectshoptp.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
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
            var utypeValue = 0
            var sertype = ""
            var refrral = ""
            var mob = ""
            var business_name = ""
             var shop_type = "yes"
            var logo: Uri? = null
            var favicon: Uri? = null
            var thumbnail: Uri? = null
            var theme_color = ""
            var url = ArrayList<String>()
            var icon = ArrayList<String>()

            //            var url = ""
//            var icon = ""
            var cname = ""
            var cnameValue = 0
            var p_id = "p_id"
            var file = ""
            var featured = ""
            var featuredValue = 0
            var menu_status = ""
            var menu_statusValue = 0
            var title = ""
            var productTitle = ""
            var special_price_start = ""
            var special_price = ""
            var price_type = ""
            var price_typeValue = 0
            var price = ""
            var type = ""
            var special_price_end = ""
            var ga_measurement_id = ""
            var analytics_view_id = ""
            var astatus = ""
            var astatusValue = 0
            const val gfile = ""
            var tag_id = ""
            var tag_idName = ""
            var tstatus = ""
            var tstatusValue = 0
            var pixel_id = ""
            var pstatus = ""
            var pstatusValue = 0
            var number = ""
            var shop_page_pretext = ""
            var other_page_pretext = ""
            var wstatus = ""
            var wstatusValue = 0

        }


    }

    fun reset() {
        name = ""
        email = ""
        password = ""
        domain = ""
        custom_domain = ""
        utype = ""
        utypeValue = 0
        sertype = ""
        refrral = ""
        mob = ""
        business_name = ""
        logo = null
        favicon = null
        thumbnail = null
          theme_color = ""
          url = ArrayList<String>()
          icon = ArrayList<String>()

//            var url = ""
//            var icon = ""
          cname = ""
          cnameValue = 0
            p_id = ""
          file = ""
          featured = ""
          featuredValue = 0
          menu_status = ""
          menu_statusValue = 0
          title = ""
          productTitle = ""
          special_price_start = ""
          special_price = ""
          price_type = ""
          price_typeValue = 0
          price = ""
          type = ""
          special_price_end = ""
          ga_measurement_id = ""
          analytics_view_id = ""
          astatus = ""
          astatusValue = 0
           tag_id = ""
          tag_idName = ""
          tstatus = ""
          tstatusValue = 0
          pixel_id = ""
          pstatus = ""
          pstatusValue = 0
          number = ""
          shop_page_pretext = ""
          other_page_pretext = ""
          wstatus = ""
          wstatusValue = 0
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
