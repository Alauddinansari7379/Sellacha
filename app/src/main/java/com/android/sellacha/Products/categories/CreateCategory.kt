package com.android.sellacha.Products.categories

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.android.sellacha.Products.categories.Model.ModelFeatured
import com.android.sellacha.Products.categories.Model.ModelGender
import com.android.sellacha.R
import com.android.sellacha.api.model.categoriesDM
import com.android.sellacha.databinding.FragmentCreateCategoryBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.example.ehcf.Fragment.test.UploadResponse
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import com.google.android.material.snackbar.Snackbar
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class CreateCategory : Fragment(), UploadRequestBody.UploadCallback {
    private lateinit var binding: FragmentCreateCategoryBinding
    private lateinit var sessionManager: SessionManager
    var navController: NavController? = null
    val handler = Handler(Looper.getMainLooper())
    private var selectedImageUri: Uri? = null
    val REQUEST_CODE_IMAGE = 101
    var image_viewAddRe: ImageView? = null
    var progressDialog: ProgressDialog? = null
    var categoryList = ArrayList<ModelGender>()
    var featuredList = ArrayList<ModelFeatured>()
    var assignMenuList = ArrayList<ModelFeatured>()
    var imageView: ImageView? = null
    var categoriesArr = ArrayList<categoriesDM>()
    private var parentCate=""
    private var featured=""
    private var assignMenu=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_category, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateCategoryBinding.bind(view)

        sessionManager = SessionManager(requireContext())


        sessionManager = SessionManager(requireContext())
//        binding.btnSelectImage.setOnClickListener {
//            startActivity(Intent(requireContext(), ImageUpload::class.java))
//        }

        binding.btnchoosefile.setOnClickListener {
            opeinImageChooser()
        }

        binding.btnSave.setOnClickListener {
            if (binding.txtName.text.isEmpty()) {
                binding.txtName.error = "First Name Required"
                binding.txtName.requestFocus()
                return@setOnClickListener
            } else{
                uploadImage()
            }

        }
        categoryList.add(ModelGender("None", -1))
        categoryList.add(ModelGender("Services", 0))
        categoryList.add(ModelGender("ServiceSchedule", 1))
        categoryList.add(ModelGender("Category", 1))
        binding.spinnerCategory.adapter = ArrayAdapter<ModelGender>(requireContext(), android.R.layout.simple_list_item_1, categoryList)

        featuredList.add(ModelFeatured("Yes", "1"))
        featuredList.add(ModelFeatured("No", "0"))
        binding.spinnerFeatured.adapter = ArrayAdapter<ModelFeatured>(requireContext(), android.R.layout.simple_list_item_1, featuredList)

        assignMenuList.add(ModelFeatured("None", "-1"))
        assignMenuList.add(ModelFeatured("Yes", "1"))
        assignMenuList.add(ModelFeatured("No", "0"))
        binding.spinnerMenu.adapter = ArrayAdapter<ModelFeatured>(requireContext(), android.R.layout.simple_list_item_1, assignMenuList)


        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (categoryList.size > 0) {
                    parentCate = categoryList[i].value.toString()
                    Log.e(ContentValues.TAG, "parentCate: $parentCate")
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        binding.spinnerFeatured.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (featuredList.size > 0) {

                    featured = featuredList[i].value.toString()

                    Log.e(ContentValues.TAG, "parentCate: $parentCate")
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        binding.spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (assignMenuList.size > 0) {

                    assignMenu = assignMenuList[i].value.toString()

                    Log.e(ContentValues.TAG, "parentCate: $parentCate")
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

    }

    private fun uploadImage() {
        if (selectedImageUri == null) {
            binding.layoutRoot.snackbar("Select an Image First")
            return
        }

        val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(
            selectedImageUri!!, "r", null

        ) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        var file = File(
            requireActivity().cacheDir, activity?.contentResolver!!.getFileName(selectedImageUri!!)
        )
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        AppProgressBar.showLoaderDialog(requireContext())

        // binding.progressBar.progress = 0
        val body = UploadRequestBody(file, "image", this)
        val name = binding.txtName.text.toString().trim()

        ApiClient.apiService.createCategory(
            sessionManager.authToken, name, parentCate, featured,assignMenu , MultipartBody.Part.createFormData("file", file.name, body),
        ).enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>, response: Response<UploadResponse>
            ) {
                 if (response.code() == 401) {
                    myToast(requireActivity(), "Maximum Attribute limit exceeded")
                    AppProgressBar.hideLoaderDialog()

                }
                 else if (response.code() == 200) {

                    myToast(requireActivity(), response.message())
                    AppProgressBar.hideLoaderDialog()

                } else {
                    myToast(requireActivity(), response.message())
                    AppProgressBar.hideLoaderDialog()

                }
                // apiCallGetPrePending1()

                //  binding.progressBar.progress = 100

            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                binding.layoutRoot.snackbar(t.message!!)
                // binding.progressBar.progress = 0
                AppProgressBar.hideLoaderDialog()

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

//        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
//        pdfIntent.type = "application/pdf"
//        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
//        startActivityForResult(pdfIntent, REQUEST_CODE_IMAGE)
//
//        //   }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE -> {
                    selectedImageUri = data?.data
                    Log.e("data?.data", data?.data.toString())
                    // binding.imageViewNew.visibility = View.VISIBLE
                    //   imageView?.setImageURI(selectedImageUri)
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


