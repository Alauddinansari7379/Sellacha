package com.android.sellacha.Registration

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.sellacha.databinding.FragmentAllUploadImageBinding
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.example.ehcf.sharedpreferences.SessionManager


class AllUploadImagesFragment : Fragment(),UploadRequestBody.UploadCallback {
    var binding: FragmentAllUploadImageBinding? = null
    private lateinit var sessionManager: SessionManager
    private var image = 0
    var urlList = ArrayList<String>()
    var iconClassList = ArrayList<String>()
    private var logo: Uri? = null
    private var thumbnail: Uri? = null
    private var favicon: Uri? = null
    var serviceList = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                com.android.sellacha.R.layout.fragment_all_upload_image,
                container,
                false
            )
        sessionManager = SessionManager(requireContext())

        serviceList.add("Clothes")
        serviceList.add("Shoes")

//
//        val bitmapLogo =
//            BitmapFactory.decodeFile(File(StoreInformation.RegistrationData.logo).toString())
//        val bitmapFavicon =
//            BitmapFactory.decodeFile(File(StoreInformation.RegistrationData.favicon).toString())
//        val bitmapThumbnail =
//            BitmapFactory.decodeFile(File(StoreInformation.RegistrationData.file).toString())

        binding!!.chooseLogo.setOnClickListener {
            image = 1
            opeinImageChooser()
        }
        binding!!.chooseFavicon.setOnClickListener {
            image = 2
            opeinImageChooser()
        }
        binding!!.chooseThumbnail.setOnClickListener {
            image = 3
            opeinImageChooser()
        }
//        binding!!.Imageview.setImageBitmap(bitmapLogo)
//        binding!!.Imageview1.setImageBitmap(bitmapFavicon)
//        binding!!.Imageview2.setImageBitmap(bitmapThumbnail)


//        binding!!.saveBtn.setOnClickListener { view: View? ->
//
//            StoreInformation.RegistrationData.number = binding!!.txtWhatsappNo.text.toString()
//            StoreInformation.RegistrationData.shop_page_pretext =
//                binding!!.txtPretextForProd.text.toString()
//            StoreInformation.RegistrationData.other_page_pretext =
//                binding!!.txtOtherPagePre.text.toString()
//            StoreInformation.RegistrationData.wstatus = binding!!.txtStatus.text.toString()

        binding!!.saveBtn.setOnClickListener {
            // apiCallRegistration()

        }

        return binding!!.root
    }


//    private fun apiCallRegistration() {
//        AppProgressBar.showLoaderDialog(requireContext())
//
////        if (logo == null) {
////            myToast(requireActivity(), "Select Logo")
////            return
////        }
////        if (favicon == null) {
////            myToast(requireActivity(), "Select Favicon")
////            return
////        }
////
////        if (thumbnail == null) {
////            myToast(requireActivity(), "Select Favicon")
////            return
////        }
////        val parcelFileDescriptorLogo =
////            activity?.contentResolver?.openFileDescriptor(logo!!, "r", null)
////                ?: return
////
////        val parcelFileDescriptorFavicon =
////            activity?.contentResolver?.openFileDescriptor(favicon!!, "r", null)
////                ?: return
////        val parcelFileDescriptorThumbnail =
////            activity?.contentResolver?.openFileDescriptor(favicon!!, "r", null)
////                ?: return
////
////        val inputStreamLogo = FileInputStream(parcelFileDescriptorLogo.fileDescriptor)
////        var logo = File(requireActivity().cacheDir, activity?.contentResolver!!.getFileName(logo!!))
////
////        val inputStreamFavicon = FileInputStream(parcelFileDescriptorFavicon.fileDescriptor)
////        var logoFavicon =
////            File(requireActivity().cacheDir, activity?.contentResolver!!.getFileName(favicon!!))
////
////        val inputStreamThumbnail = FileInputStream(parcelFileDescriptorThumbnail.fileDescriptor)
////        var thumbnail =
////            File(requireActivity().cacheDir, activity?.contentResolver!!.getFileName(thumbnail!!))
////
////        val outputStreamLogo = FileOutputStream(logo)
////        inputStreamLogo.copyTo(outputStreamLogo)
////
////
////        val outputStreamFavicon = FileOutputStream(logoFavicon)
////        inputStreamFavicon.copyTo(outputStreamFavicon)
////
////        val outputStreamThumbnail = FileOutputStream(thumbnail)
////        inputStreamThumbnail.copyTo(outputStreamThumbnail)
////
////
////        StoreInformation.RegistrationData.logo = logo.toString()
////        StoreInformation.RegistrationData.favicon = logoFavicon.toString()
//
////        Navigation.findNavController(binding!!.root)
////            .navigate(com.android.sellacha.R.id.addCategoryFragment)
//
//
////        val bodyLogo = UploadRequestBody(logo, "image", this)
////        val bodyFavicon = UploadRequestBody(logoFavicon, "image", this)
////        val bodyThumbnail = UploadRequestBody(thumbnail, "image", this)
//
////        MultipartBody.Part.createFormData("image", logo.name, bodyLogo)
////        MultipartBody.Part.createFormData("image", logoFavicon.name, bodyFavicon)
////        MultipartBody.Part.createFormData("image", thumbnail.name, bodyThumbnail)
//
//        "json".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//
//        ApiClient.apiService.registration(
//            StoreInformation.RegistrationData.name,
//            StoreInformation.RegistrationData.email,
//            StoreInformation.RegistrationData.password,
//            StoreInformation.RegistrationData.name,
//            StoreInformation.RegistrationData.custom_domain,
//            StoreInformation.RegistrationData.utype,
//            StoreInformation.RegistrationData.sertype,
//            StoreInformation.RegistrationData.refrral,
//            StoreInformation.RegistrationData.mob,
//            StoreInformation.RegistrationData.business_name,
//            StoreInformation.RegistrationData.shop_type,
//            "",
//            "",
//            StoreInformation.RegistrationData.theme_color,
//            "serviceList",
//            [{"File1"},{"File2"}],
//            StoreInformation.RegistrationData.cname,
//            StoreInformation.RegistrationData.p_id,
//            "",
//            StoreInformation.RegistrationData.featured,
//            StoreInformation.RegistrationData.menu_status,
//            StoreInformation.RegistrationData.title,
//            StoreInformation.RegistrationData.special_price_start,
//            StoreInformation.RegistrationData.special_price,
//            StoreInformation.RegistrationData.price_type,
//            StoreInformation.RegistrationData.price,
//            StoreInformation.RegistrationData.type,
//            StoreInformation.RegistrationData.special_price_end,
//            StoreInformation.RegistrationData.ga_measurement_id,
//            StoreInformation.RegistrationData.analytics_view_id,
//            StoreInformation.RegistrationData.astatus,
//            StoreInformation.RegistrationData.gfile,
//            StoreInformation.RegistrationData.tag_id,
//            StoreInformation.RegistrationData.tstatus,
//            StoreInformation.RegistrationData.pixel_id,
//            StoreInformation.RegistrationData.pstatus,
//            StoreInformation.RegistrationData.number,
//            StoreInformation.RegistrationData.shop_page_pretext,
//            StoreInformation.RegistrationData.other_page_pretext,
//            StoreInformation.RegistrationData.wstatus,
//        )
//            .enqueue(object : Callback<ModelRegistration> {
//                @SuppressLint("LogNotTimber")
//                override fun onResponse(
//                    call: Call<ModelRegistration>, response: Response<ModelRegistration>
//                ) {
//                    if (response.code() == 500) {
//                        myToast(requireActivity(), "Server Error")
//                        AppProgressBar.hideLoaderDialog()
//
//
//                    } else if (response.code() == 401) {
//                        myToast(requireActivity(), response.body()!!.data.msg)
//                        AppProgressBar.hideLoaderDialog()
//
//                    } else if (response.body()!!.success.toString() == "false") {
//                        myToast(requireActivity(), response.body()!!.message)
//                        AppProgressBar.hideLoaderDialog()
//
//                    } else if (response.body()!!.success.toString() == "true") {
//                        startActivity(Intent(context, HomeDashBoard::class.java))
//                        activity!!.overridePendingTransition(
//                            R.anim.bottom_anim,
//                            R.anim.bottom_out_anim
//                        )
//                        AppProgressBar.hideLoaderDialog()
//                        sessionManager.isLogin = true
//
//                    } else {
//                        myToast(requireActivity(), response.body()!!.data.msg)
//                    }
//                }
//
//                override fun onFailure(call: Call<ModelRegistration>, t: Throwable) {
//                    myToast(requireActivity(), "Something went wrong")
//                    AppProgressBar.hideLoaderDialog()
//
//
//                }
//
//            })
//    }

//private open fun requestUploadSurvey() {
//    val propertyImageFile: File = File(surveyModel.getPropertyImagePath())
//    val propertyImage: RequestBody = create(
//        MediaType.parse("image/*"),
//        propertyImageFile
//    )
//    val propertyImagePart: Part = createFormData.createFormData("PropertyImage", propertyImageFile.name, propertyImage)
//    val surveyImagesParts: Array<Part?> = arrayOfNulls<Part>(surveyModel.getPicturesList().size()
//    )
//    for (index in 0 until surveyModel.getPicturesList().size()) {
//        Log.d(TAG, "requestUploadSurvey: survey image " +
//                index +
//                    "  " +
//                    surveyModel.getPicturesList()
//                        .get(index)
//                        .getImagePath()
//        )
//        val file: File = File(surveyModel.getPicturesList().get(index).getImagePath())
//        val surveyBody: RequestBody = create(
//            MediaType.parse("image/*"),
//            file)
//        surveyImagesParts!![index] = createFormData.createFormData(
//            "SurveyImage",
//            file.name,
//            surveyBody
//        )
//    }
//    val webServicesAPI: WebServicesAPI = RetrofitManager.getInstance()
//        .getRetrofit()
//        .create(WebServicesAPI::class.java)
//    var surveyResponse: Call<UploadSurveyResponseModel?>? = null
//    if (surveyImagesParts != null) {
//        surveyResponse = webServicesAPI.uploadSurvey(
//            surveyImagesParts,
//            propertyImagePart,
//            draBody
//        )
//    }
//    surveyResponse!!.enqueue(this)
//}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ColorSchemeFragment.REQUEST_CODE_IMAGE -> {
                    when (image) {
                        1 -> {
                            logo = data?.data
                            binding!!.logotxtNoFile.text = "Logo Selected"
                            binding!!.logotxtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));

                        }
                        2 -> {
                            favicon = data?.data
                            binding!!.favtxtNoFile.text = "Favicon Selected"
                            binding!!.favtxtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));

                        }

                        3 -> {
                            thumbnail = data?.data
                            binding!!.ThumbnailNoFile.text = "Thumbnai Selected"
                            binding!!.ThumbnailNoFile.setTextColor(Color.parseColor("#FF4CAF50"));

                        }
                    }
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