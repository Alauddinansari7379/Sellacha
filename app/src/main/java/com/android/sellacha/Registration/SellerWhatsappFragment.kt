package com.android.sellacha.Registration

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.sellacha.LogIn.LoginActivity
import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.Registration.Model.ModelRegJava
import com.android.sellacha.databinding.FragmentSellerWhatsappBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.ImageUploadClass.UploadRequestBody
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerWhatsappFragment : Fragment(), UploadRequestBody.UploadCallback {
    var binding: FragmentSellerWhatsappBinding? = null
    private lateinit var sessionManager: SessionManager
    var logoString = ""
    var statuseList = ArrayList<ModelProductType>()

    private var logo: Uri? = null
    private var favicon: Uri? = null
    private var thumbnail: Uri? = null
    private var image = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_seller_whatsapp, container, false)
        sessionManager = SessionManager(requireContext())


        binding!!.txtWhatsappNo.setText(StoreInformation.RegistrationData.number)
        binding!!.txtPretextForProd.setText(StoreInformation.RegistrationData.shop_page_pretext)
        binding!!.txtOtherPagePre.setText(StoreInformation.RegistrationData.other_page_pretext)
        binding!!.txtStatus.setSelection(StoreInformation.RegistrationData.wstatusValue)


//        val bitmapLogo =
//            BitmapFactory.decodeFile(File(StoreInformation.RegistrationData.logo).toString())
//        val bitmapFavicon =
//            BitmapFactory.decodeFile(File(StoreInformation.RegistrationData.favicon).toString())
//        val bitmapThumbnail =
//            BitmapFactory.decodeFile(File(StoreInformation.RegistrationData.file).toString())


//        binding!!.Imageview.setImageBitmap(bitmapLogo)
//        binding!!.Imageview1.setImageBitmap(bitmapFavicon)
//        binding!!.Imageview2.setImageBitmap(bitmapThumbnail)


        binding!!.saveBtn.setOnClickListener { view: View? ->

//            if (binding!!.txtWhatsappNo.text.isEmpty()) {
//                binding!!.txtWhatsappNo.error = "Enter WhatsApp Number"
//                binding!!.txtWhatsappNo.requestFocus()
//                return@setOnClickListener
//            }
//            if (binding!!.txtPretextForProd.text.isEmpty()) {
//                binding!!.txtPretextForProd.error = "Enter Pretext For Product Page"
//                binding!!.txtPretextForProd.requestFocus()
//                return@setOnClickListener
//            }
//
//            if (binding!!.txtOtherPagePre.text.isEmpty()) {
//                binding!!.txtOtherPagePre.error = "Enter Other Page Pretext"
//                binding!!.txtOtherPagePre.requestFocus()
//                return@setOnClickListener
//            }

            StoreInformation.RegistrationData.number = binding!!.txtWhatsappNo.text.toString()
            StoreInformation.RegistrationData.shop_page_pretext =
                binding!!.txtPretextForProd.text.toString()
            StoreInformation.RegistrationData.other_page_pretext =
                binding!!.txtOtherPagePre.text.toString()

            Log.e(
                "StoreInformation.RegistrationData.name",
                StoreInformation.RegistrationData.business_name.replace(" ", "")
            )
            apiCallRegistration()
//custom domain
            //shop_type
            //p_id
            // astutes


//            Log.e("StoreInformation.RegistrationData.name",StoreInformation.RegistrationData.name,)
//            Log.e("StoreInformation.RegistrationData.password",StoreInformation.RegistrationData.password,)
//            Log.e("StoreInformation.RegistrationData.name",StoreInformation.RegistrationData.name,)
//            Log.e("StoreInformation.RegistrationData.custom_domain",StoreInformation.RegistrationData.custom_domain,)
//            Log.e("StoreInformation.RegistrationData.utype",StoreInformation.RegistrationData.utype,)
//            Log.e("StoreInformation.RegistrationData.sertype",StoreInformation.RegistrationData.sertype,)
//            Log.e("StoreInformation.RegistrationData.refrral",StoreInformation.RegistrationData.refrral,)
//            Log.e("StoreInformation.RegistrationData.mob",StoreInformation.RegistrationData.mob,)
//            Log.e("StoreInformation.RegistrationData.business_name",StoreInformation.RegistrationData.business_name,)
//            Log.e("StoreInformation.RegistrationData.shop_type",StoreInformation.RegistrationData.shop_type,)
//            Log.e("StoreInformation.RegistrationData.theme_color",StoreInformation.RegistrationData.theme_color,)
//            Log.e("StoreInformation.RegistrationData.url",
//                StoreInformation.RegistrationData.url.toString(),)
//            Log.e("StoreInformation.RegistrationData.cname",StoreInformation.RegistrationData.cname,)
//            Log.e("StoreInformation.RegistrationData.p_id",StoreInformation.RegistrationData.p_id,)
//            Log.e("StoreInformation.RegistrationData.featured",StoreInformation.RegistrationData.featured,)
//            Log.e("StoreInformation.RegistrationData.menu_status",StoreInformation.RegistrationData.menu_status,)
//            Log.e("StoreInformation.RegistrationData.title",StoreInformation.RegistrationData.title,)
//            Log.e("StoreInformation.RegistrationData.special_price_start",StoreInformation.RegistrationData.special_price_start,)
//            Log.e("StoreInformation.RegistrationData.special_price",StoreInformation.RegistrationData.special_price,)
//            Log.e("StoreInformation.RegistrationData.price_type",StoreInformation.RegistrationData.price_type,)
//            Log.e("StoreInformation.RegistrationData.price",StoreInformation.RegistrationData.price,)
//            Log.e("StoreInformation.RegistrationData.type",StoreInformation.RegistrationData.type,)
//            Log.e("StoreInformation.RegistrationData.special_price_end",StoreInformation.RegistrationData.special_price_end,)
//            Log.e("StoreInformation.RegistrationData.ga_measurement_id",StoreInformation.RegistrationData.ga_measurement_id,)
//            Log.e("StoreInformation.RegistrationData.analytics_view_id",StoreInformation.RegistrationData.analytics_view_id,)
//            Log.e("StoreInformation.RegistrationData.astatus",StoreInformation.RegistrationData.astatus,)
//            Log.e("StoreInformation.RegistrationData.gfile",StoreInformation.RegistrationData.gfile,)
//            Log.e("StoreInformation.RegistrationData.tag_id",StoreInformation.RegistrationData.tag_id,)
//            Log.e("StoreInformation.RegistrationData.tstatus",StoreInformation.RegistrationData.tstatus,)
//            Log.e("StoreInformation.RegistrationData.pixel_id",StoreInformation.RegistrationData.pixel_id,)
//            Log.e("StoreInformation.RegistrationData.pstatus",StoreInformation.RegistrationData.pstatus,)
//            Log.e("StoreInformation.RegistrationData.number",StoreInformation.RegistrationData.number,)
//            Log.e("StoreInformation.RegistrationData.shop_page_pretext",StoreInformation.RegistrationData.shop_page_pretext,)
//            Log.e("StoreInformation.RegistrationData.other_page_pretext",StoreInformation.RegistrationData.other_page_pretext,)
//            Log.e("StoreInformation.RegistrationData.wstatus",StoreInformation.RegistrationData.wstatus,)

            // apiCallRegistration()

            //   Navigation.findNavController(binding!!.root).navigate(com.android.sellacha.R.id.allImageUploadFragment)

        }
        statuseList.add(ModelProductType("Enable", 1))
        statuseList.add(ModelProductType("Disable", 2))

        binding!!.txtStatus.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            statuseList
        )


        binding!!.txtStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (statuseList.size > 0) {
                        StoreInformation.RegistrationData.wstatus = statuseList[i].text
                        StoreInformation.RegistrationData.wstatusValue = statuseList[i].value


                        //  Log.e(ContentValues.TAG, "statuse: $statuse")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }


        // apiCallRegistration()


        // }
        return binding!!.root
    }


    @SuppressLint("SuspiciousIndentation")
    private fun apiCallRegistration() {
        AppProgressBar.showLoaderDialog(requireContext())
//
//
//        if (StoreInformation.RegistrationData.thumbnail == null) {
//            StoreInformation.RegistrationData.thumbnail = StoreInformation.RegistrationData.logo
//        }
//        val parcelFileDescriptorLogo =
//            activity?.contentResolver?.openFileDescriptor(
//                StoreInformation.RegistrationData.logo!!,
//                "r",
//                null
//            )
//                ?: return
//
//        val parcelFileDescriptorFavicon =
//            activity?.contentResolver?.openFileDescriptor(
//                StoreInformation.RegistrationData.favicon!!,
//                "r",
//                null
//            )
//                ?: return
//        val parcelFileDescriptorThumbnail =
//            activity?.contentResolver?.openFileDescriptor(
//                StoreInformation.RegistrationData.thumbnail!!,
//                "r",
//                null
//            )
//                ?: return
//
//        val inputStreamLogo = FileInputStream(parcelFileDescriptorLogo.fileDescriptor)
//        var logo = File(
//            requireActivity().cacheDir,
//            activity?.contentResolver!!.getFileName(StoreInformation.RegistrationData.logo!!)
//        )
//
//        val inputStreamFavicon = FileInputStream(parcelFileDescriptorFavicon.fileDescriptor)
//        var logoFavicon = File(
//            requireActivity().cacheDir,
//            activity?.contentResolver!!.getFileName(StoreInformation.RegistrationData.favicon!!)
//        )
//
//        val inputStreamThumbnail = FileInputStream(parcelFileDescriptorThumbnail.fileDescriptor)
//        var thumbnail = File(
//            requireActivity().cacheDir,
//            activity?.contentResolver!!.getFileName(StoreInformation.RegistrationData.thumbnail!!!!)
//        )
//
//        val outputStreamLogo = FileOutputStream(logo)
//        inputStreamLogo.copyTo(outputStreamLogo)
//
//
//        val outputStreamFavicon = FileOutputStream(logoFavicon)
//        inputStreamFavicon.copyTo(outputStreamFavicon)
//
//        val outputStreamThumbnail = FileOutputStream(thumbnail)
//        inputStreamThumbnail.copyTo(outputStreamThumbnail)

//
//          = logo.toString()
//        StoreInformation.RegistrationData.favicon= logoFavicon.toString()

        // Navigation.findNavController(binding!!.root).navigate(com.android.sellacha.R.id.addCategoryFragment)


//        val bodyLogo = UploadRequestBody(logo, "image", this)
//        val bodyFavicon = UploadRequestBody(logoFavicon, "image", this)
//        val bodyThumbnail = UploadRequestBody(thumbnail, "image", this)
//
//        MultipartBody.Part.createFormData("image", logo.name, bodyLogo)
//        MultipartBody.Part.createFormData("image", logoFavicon.name, bodyFavicon)
//        MultipartBody.Part.createFormData("image", thumbnail.name, bodyThumbnail)

        //   "json".toRequestBody("multipart/form-data".toMediaTypeOrNull())


        Log.e("StoreInformation.RegistrationData.name", StoreInformation.RegistrationData.name)
        Log.e(
            "StoreInformation.RegistrationData.password",
            StoreInformation.RegistrationData.password,
        )
        Log.e("StoreInformation.RegistrationData.name", StoreInformation.RegistrationData.name)
        Log.e(
            "StoreInformation.RegistrationData.custom_domain",
            StoreInformation.RegistrationData.custom_domain,
        )
        Log.e("StoreInformation.RegistrationData.utype", StoreInformation.RegistrationData.utype)
        Log.e(
            "StoreInformation.RegistrationData.sertype",
            StoreInformation.RegistrationData.sertype,
        )
        Log.e(
            "StoreInformation.RegistrationData.refrral",
            StoreInformation.RegistrationData.refrral,
        )
        Log.e("StoreInformation.RegistrationData.mob", StoreInformation.RegistrationData.mob)
        Log.e(
            "StoreInformation.RegistrationData.business_name",
            StoreInformation.RegistrationData.business_name,
        )
        Log.e(
            "StoreInformation.RegistrationData.shop_type",
            StoreInformation.RegistrationData.shop_type,
        )
        Log.e(
            "StoreInformation.RegistrationData.theme_color",
            StoreInformation.RegistrationData.theme_color,
        )
        Log.e(
            "StoreInformation.RegistrationData.url",
            StoreInformation.RegistrationData.url.toString(),
        )
        Log.e("StoreInformation.RegistrationData.cname", StoreInformation.RegistrationData.cname)
        Log.e("StoreInformation.RegistrationData.p_id", StoreInformation.RegistrationData.p_id)
        Log.e(
            "StoreInformation.RegistrationData.featured",
            StoreInformation.RegistrationData.featured,
        )
        Log.e(
            "StoreInformation.RegistrationData.menu_status",
            StoreInformation.RegistrationData.menu_status,
        )
        Log.e("StoreInformation.RegistrationData.title", StoreInformation.RegistrationData.title)
        Log.e(
            "StoreInformation.RegistrationData.special_price_start",
            StoreInformation.RegistrationData.special_price_start,
        )
        Log.e(
            "StoreInformation.RegistrationData.special_price",
            StoreInformation.RegistrationData.special_price,
        )
        Log.e(
            "StoreInformation.RegistrationData.price_type",
            StoreInformation.RegistrationData.price_type,
        )
        Log.e("StoreInformation.RegistrationData.price", StoreInformation.RegistrationData.price)
        Log.e("StoreInformation.RegistrationData.type", StoreInformation.RegistrationData.type)
        Log.e(
            "StoreInformation.RegistrationData.special_price_end",
            StoreInformation.RegistrationData.special_price_end,
        )
        Log.e(
            "StoreInformation.RegistrationData.ga_measurement_id",
            StoreInformation.RegistrationData.ga_measurement_id,
        )
        Log.e(
            "StoreInformation.RegistrationData.analytics_view_id",
            StoreInformation.RegistrationData.analytics_view_id,
        )
        Log.e(
            "StoreInformation.RegistrationData.astatus",
            StoreInformation.RegistrationData.astatus,
        )
        Log.e("StoreInformation.RegistrationData.gfile", StoreInformation.RegistrationData.gfile)
        Log.e("StoreInformation.RegistrationData.tag_id", StoreInformation.RegistrationData.tag_id)
        Log.e(
            "StoreInformation.RegistrationData.tstatus",
            StoreInformation.RegistrationData.tstatus,
        )
        Log.e(
            "StoreInformation.RegistrationData.pixel_id",
            StoreInformation.RegistrationData.pixel_id,
        )
        Log.e(
            "StoreInformation.RegistrationData.pstatus",
            StoreInformation.RegistrationData.pstatus,
        )
        Log.e("StoreInformation.RegistrationData.number", StoreInformation.RegistrationData.number)
        Log.e(
            "StoreInformation.RegistrationData.shop_page_pretext",
            StoreInformation.RegistrationData.shop_page_pretext,
        )
        Log.e(
            "StoreInformation.RegistrationData.other_page_pretext",
            StoreInformation.RegistrationData.other_page_pretext,
        )
        Log.e(
            "StoreInformation.RegistrationData.wstatus",
            StoreInformation.RegistrationData.wstatus,
        )


        if (StoreInformation.RegistrationData.title.isEmpty()) {
            StoreInformation.RegistrationData.title = "null"

        }
        if (StoreInformation.RegistrationData.price_type.isEmpty()) {
            StoreInformation.RegistrationData.price_type = "null"

        }
        if (StoreInformation.RegistrationData.cname.isEmpty()) {
            StoreInformation.RegistrationData.cname = "null"

        }

        if (StoreInformation.RegistrationData.featured.isEmpty()) {
            StoreInformation.RegistrationData.featured = "null"

        }
        if (StoreInformation.RegistrationData.menu_status.isEmpty()) {
            StoreInformation.RegistrationData.menu_status = "null"

        }
        if (StoreInformation.RegistrationData.special_price_start.isEmpty()) {
            StoreInformation.RegistrationData.special_price_start = "null"

        }
        if (StoreInformation.RegistrationData.special_price.isEmpty()) {
            StoreInformation.RegistrationData.special_price = "null"

        }
        if (StoreInformation.RegistrationData.price_type.isEmpty()) {
            StoreInformation.RegistrationData.price_type = "null"

        }
        if (StoreInformation.RegistrationData.price.isEmpty()) {
            StoreInformation.RegistrationData.price = "null"

        }
        if (StoreInformation.RegistrationData.type.isEmpty()) {
            StoreInformation.RegistrationData.type = "null"

        }
//        if (StoreInformation.RegistrationData.special_price_end.isEmpty()) {
//            StoreInformation.RegistrationData.special_price_end="null"
//
//        }
//        if (StoreInformation.RegistrationData.ga_measurement_id.isEmpty()) {
//            StoreInformation.RegistrationData.ga_measurement_id="null"
//
//        }
//        if (StoreInformation.RegistrationData.analytics_view_id.isEmpty()) {
//            StoreInformation.RegistrationData.analytics_view_id="null"
//
//        }
//        if (StoreInformation.RegistrationData.astatus.isEmpty()) {
//            StoreInformation.RegistrationData.astatus="null"
//
//        }
//        if (StoreInformation.RegistrationData.tag_id.isEmpty()) {
//            StoreInformation.RegistrationData.tag_id="null"
//
//        }
//        if (StoreInformation.RegistrationData.tstatus.isEmpty()) {
//            StoreInformation.RegistrationData.tstatus="null"
//
//        }
//        if (StoreInformation.RegistrationData.pixel_id.isEmpty()) {
//            StoreInformation.RegistrationData.pixel_id="null"
//
//        }
//        if (StoreInformation.RegistrationData.pstatus.isEmpty()) {
//            StoreInformation.RegistrationData.pstatus="null"
//
//        }
//        if (StoreInformation.RegistrationData.number.isEmpty()) {
//            StoreInformation.RegistrationData.number="null"
//
//        }
//        if (StoreInformation.RegistrationData.shop_page_pretext.isEmpty()) {
//            StoreInformation.RegistrationData.shop_page_pretext="null"
//
//        }
//        if (StoreInformation.RegistrationData.other_page_pretext.isEmpty()) {
//            StoreInformation.RegistrationData.other_page_pretext="null"
//
//        }
//        if (StoreInformation.RegistrationData.wstatus.isEmpty()) {
//            StoreInformation.RegistrationData.wstatus="null"
//
//        }

        ApiClient.apiService.registration(
            StoreInformation.RegistrationData.business_name,
            StoreInformation.RegistrationData.email,
            StoreInformation.RegistrationData.password,
            StoreInformation.RegistrationData.business_name.replace(" ", ""),
            StoreInformation.RegistrationData.business_name,
            StoreInformation.RegistrationData.utype,
            StoreInformation.RegistrationData.sertype,
            StoreInformation.RegistrationData.refrral,
            StoreInformation.RegistrationData.mob,
            StoreInformation.RegistrationData.business_name,
            StoreInformation.RegistrationData.shop_type,
//            MultipartBody.Part.createFormData("logo", logo.name, bodyLogo),
//            MultipartBody.Part.createFormData("favicon", logoFavicon.name, bodyFavicon),
            StoreInformation.RegistrationData.theme_color,
            StoreInformation.RegistrationData.url,
            StoreInformation.RegistrationData.cname,
            StoreInformation.RegistrationData.p_id,
         //   MultipartBody.Part.createFormData("file", thumbnail.name, bodyThumbnail),
            StoreInformation.RegistrationData.featured,
            StoreInformation.RegistrationData.menu_status,
            StoreInformation.RegistrationData.title,
            StoreInformation.RegistrationData.special_price_start,
            StoreInformation.RegistrationData.special_price,
            StoreInformation.RegistrationData.price_type,
            StoreInformation.RegistrationData.price,
            StoreInformation.RegistrationData.type,
            StoreInformation.RegistrationData.special_price_end,
            StoreInformation.RegistrationData.ga_measurement_id,
            StoreInformation.RegistrationData.analytics_view_id,
            StoreInformation.RegistrationData.astatus,
            StoreInformation.RegistrationData.gfile,
            StoreInformation.RegistrationData.tag_id,
            StoreInformation.RegistrationData.tstatus,
            StoreInformation.RegistrationData.pixel_id,
            StoreInformation.RegistrationData.pstatus,
            StoreInformation.RegistrationData.number,
            StoreInformation.RegistrationData.shop_page_pretext,
            StoreInformation.RegistrationData.other_page_pretext,
            StoreInformation.RegistrationData.wstatus,
            "1",
            "1",
        )
            .enqueue(object : Callback<ModelRegJava> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelRegJava>, response: Response<ModelRegJava>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()
                        }
                        else if (!response.body()!!.success) {
                            Log.e("Error",response.body()!!.data.errors)
                            Log.e("Error",response.body()!!.data.errors)
                            myToast(requireActivity(), response.body()!!.data.errors)
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.body()!!.success) {
                            myToast(requireActivity(), response.body()!!.data.msg)
                            Log.e("Error",response.body()!!.data.errors)
                            Domain=response.body()!!.data.domain
                            AppProgressBar.hideLoaderDialog()

//                            val browse = Intent(Intent.ACTION_VIEW, Uri.parse("${response.body()!!.data.redurl}"))
//                            startActivity(browse)

                            val i = Intent(context as Activity, LoginActivity::class.java)
                                .putExtra("redurl", response.body()!!.data.redurl)
                                .putExtra("pay", "1")
                             i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(i)
                          //  activity!!.overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim)

//                            val intent = Intent(context as Activity, LoginActivity::class.java)
//                                .putExtra("domain", response.body()!!.data.domain)
//                            (context as Activity).startActivity(intent)
//                             activity!!.overridePendingTransition(R.anim.bottom_anim, R.anim.bottom_out_anim)
//                            AppProgressBar.hideLoaderDialog()

                        }
                    } catch (e: Exception) {
                        AppProgressBar.hideLoaderDialog()
                        myToast(requireActivity(), "Something went wrong")
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelRegJava>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    companion object {
        var Domain = ""
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ColorSchemeFragment.REQUEST_CODE_IMAGE -> {
//                    when (image) {
//                        1 -> {
//                            logo = data?.data
//                            binding!!.logotxtNoFile.text = "Logo Selected"
//                            binding!!.logotxtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));
//
//                        }
//                        2 -> {
//                            favicon = data?.data
//                            binding!!.txtNoFile.text = "Favicon Selected"
//                            binding!!.txtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));
//
//                        }
//                    }
                    Log.e("data?.data", data?.data.toString())
                }
            }
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
