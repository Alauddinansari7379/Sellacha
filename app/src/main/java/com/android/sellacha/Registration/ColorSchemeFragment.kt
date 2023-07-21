package com.android.sellacha.Registration

import android.annotation.SuppressLint
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
import androidx.navigation.Navigation.findNavController
import com.android.sellacha.databinding.FragmentColorSchemeBinding
import com.android.sellacha.helper.myToast
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ColorSchemeFragment : Fragment(),UploadRequestBody.UploadCallback {
    var binding: FragmentColorSchemeBinding? = null
    private var image=0
    var urlList = ArrayList<String>()
    var iconClassList = ArrayList<String>()
    private var logo: Uri? = null
    private var favicon: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, com.android.sellacha.R.layout.fragment_color_scheme, container, false)




        if (StoreInformation.RegistrationData.logo!=null){
            binding!!.logotxtNoFile.text="Logo Selected"
            binding!!.logotxtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));
            binding!!.txtThemeColr.setText(StoreInformation.RegistrationData.theme_color)
            binding!!.tvCountURL.text = urlList.size.toString()
            binding!!.txtNoFile.text="Favicon Selected"
            binding!!.txtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));
        }



        binding!!.saveBtn.setOnClickListener { view: View? ->

            if (binding!!.txtThemeColr.text.isEmpty()) {
                binding!!.txtThemeColr.error = "Enter Theme Color"
                binding!!.txtThemeColr.requestFocus()
                return@setOnClickListener
            }

           // StoreInformation.RegistrationData.url= binding!!.urlLink.text.toString()
          //  StoreInformation.RegistrationData.icon= binding!!.orderStatus.text.toString()
            StoreInformation.RegistrationData.theme_color = binding!!.txtThemeColr.text.toString()
            uploadImage()

            //  findNavController(binding!!.root).navigate(R.id.addCategoryFragment)
        }

        binding!!.addNewBtn.setOnClickListener {
            urlList.add(binding!!.urlLink.text.toString())
            iconClassList.add(binding!!.orderStatus.text.toString())
            StoreInformation.RegistrationData.url = urlList
            StoreInformation.RegistrationData.icon = iconClassList
            binding!!.urlLink.text.clear()
            binding!!.orderStatus.text.clear()
            myToast(requireActivity(), "Added")
            binding!!.urlLink.requestFocus()
            binding!!.tvCountURL.text = urlList.size.toString()
        }

        binding!!.removeBtn.setOnClickListener {
            StoreInformation.RegistrationData.url.clear()
            StoreInformation.RegistrationData.icon.clear()
            binding!!.urlLink.text.clear()
            binding!!.orderStatus.text.clear()
            myToast(requireActivity(), "All URL Removed")
            binding!!.tvCountURL.text = urlList.size.toString()

        }

        binding!!.chooseLogo.setOnClickListener {
            image = 1
            opeinImageChooser()

        }
        binding!!.chooseFavicon.setOnClickListener {
            image = 2
            opeinImageChooser()

        }

        binding!!.colorSchemeLb.setOnClickListener {
          //  Log.e("logo", StoreInformation.RegistrationData.logo)
          //  Log.e("favicon", StoreInformation.RegistrationData.favicon)
        }

        //   uploadImage()


        return binding!!.root

    }

    @SuppressLint("SuspiciousIndentation")
    private fun uploadImage() {
        if (StoreInformation.RegistrationData.logo == null) {
            myToast(requireActivity(),"Select Logo")
            return
        }

        if (StoreInformation.RegistrationData.favicon == null) {
            myToast(requireActivity(),"Select Favicon")
            return
        }
        val parcelFileDescriptorLogo =
            activity?.contentResolver?.openFileDescriptor(StoreInformation.RegistrationData.logo!!, "r", null)
                ?: return

        val parcelFileDescriptorFavicon =
            activity?.contentResolver?.openFileDescriptor(StoreInformation.RegistrationData.favicon!!, "r", null)
                ?: return

        val inputStreamLogo = FileInputStream(parcelFileDescriptorLogo.fileDescriptor)

        var logo = File(requireActivity().cacheDir, activity?.contentResolver!!.getFileName(StoreInformation.RegistrationData.logo!!))

        val inputStreamFavicon = FileInputStream(parcelFileDescriptorFavicon.fileDescriptor)
        var logoFavicon = File(requireActivity().cacheDir, activity?.contentResolver!!.getFileName(StoreInformation.RegistrationData.favicon!!))

        val outputStreamLogo = FileOutputStream(logo)
        inputStreamLogo.copyTo(outputStreamLogo)


        val outputStreamFavicon = FileOutputStream(logoFavicon)
        inputStreamFavicon.copyTo(outputStreamFavicon)


//        StoreInformation.RegistrationData.logo  = logo.toString()
//        StoreInformation.RegistrationData.favicon= logoFavicon.toString()

        findNavController(binding!!.root).navigate(com.android.sellacha.R.id.addCategoryFragment)


        val body = UploadRequestBody(logo, "image", this)

        MultipartBody.Part.createFormData("image", logo.name, body)

        "json".toRequestBody("multipart/form-data".toMediaTypeOrNull())

   }

    private fun opeinImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            (MediaStore.ACTION_IMAGE_CAPTURE)
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE)

//        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
//        pdfIntent.type = "application/pdf"
//        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
//        startActivityForResult(pdfIntent, REQUEST_CODE_IMAGE)
//
           }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE -> {
                    when(image){
                        1->{
                            StoreInformation.RegistrationData.logo=data?.data
                            binding!!.logotxtNoFile.text="Logo Selected"
                            binding!!.logotxtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));

                        }
                        2->{
                            StoreInformation.RegistrationData.favicon=data?.data
                            binding!!.txtNoFile.text="Favicon Selected"
                            binding!!.txtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));

                        }
                    }
                    Log.e("data?.data", data?.data.toString())
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_IMAGE = 101
    }

    override fun onProgressUpdate(percentage: Int) {
        //   binding.progressBar.progress = percentage
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

}