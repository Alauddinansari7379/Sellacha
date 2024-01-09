package com.android.sellacha.shopSetting.activity

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
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentCreateSliderBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.shopSetting.model.ModelSlider
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.utils.ImageUploadClass.UploadRequestBody
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class CreateSliderFragment : Fragment(), UploadRequestBody.UploadCallback {
    var binding: FragmentCreateSliderBinding? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null
    var url = "#"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_slider, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateSliderBinding.bind(view)

        sessionManager = SessionManager(requireContext())

        binding!!.btnchoosefile.setOnClickListener {
            opeinImageChooser()
        }

        binding!!.saveBtn.setOnClickListener {
            uploadImage()
        }

    }

    private fun uploadImage() {
        if (selectedImageUri == null) {
            myToast(requireActivity(), "Select an Image First")
            return
        }

        val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(
            selectedImageUri!!, "r", null

        ) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        var file = File(
            requireActivity().cacheDir,
            activity?.contentResolver!!.getFileName(selectedImageUri!!)
        )
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        // binding.progressBar.progress = 0
        val body = UploadRequestBody(file, "image", this)

        AppProgressBar.showLoaderDialog(requireContext())

        url = binding!!.txtName.text.toString().ifEmpty {
            "#"
        }

        ApiClient.apiService.addSlider(
            sessionManager.authToken,
            url,
            "",
            ",",
            MultipartBody.Part.createFormData("file", file.name, body)
        )
            .enqueue(object : Callback<ModelSlider> {
                override fun onResponse(
                    call: Call<ModelSlider>, response: Response<ModelSlider>
                ) {
                    try {
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 200) {
                            myToast(requireActivity(), response.body()!!.data)
                            Navigation.findNavController(binding!!.root)
                                .navigate(R.id.SliderFragment)
                            AppProgressBar.hideLoaderDialog()
                        } else {
                            AppProgressBar.hideLoaderDialog()
                            myToast(requireActivity(), "Something went Wrong")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(call: Call<ModelSlider>, t: Throwable) {
                  //  myToast(requireActivity(), "Something went Wrong")
                    AppProgressBar.hideLoaderDialog()
                    uploadImage()
                    //  uploadImage()
                    // AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun opeinImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            (MediaStore.ACTION_IMAGE_CAPTURE)
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE)
//
//        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
//        pdfIntent.type = "application/pdf"
//        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
//        startActivityForResult(pdfIntent, REQUEST_CODE_IMAGE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE -> {
                    selectedImageUri = data?.data
                    Log.e("data?.data", data?.data.toString())
                    binding!!.txtNoFile.text = "Image Selected"
                    binding!!.txtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));
                    //  binding.imageViewNew.visibility = View.VISIBLE
                    //   imageView?.setImageURI(selectedImageUri)
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_IMAGE = 101
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
    }

}