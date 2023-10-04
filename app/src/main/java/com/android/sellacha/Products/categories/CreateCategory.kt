package com.android.sellacha.Products.categories

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
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
import androidx.navigation.Navigation
import com.android.sellacha.Products.Attributes.activity.AttributeFragment
import com.android.sellacha.Products.categories.Model.ModelCreCatogoryJava
import com.android.sellacha.Products.categories.Model.ModelFeatured
import com.android.sellacha.Products.categories.Model.ModelGender
import com.android.sellacha.R
import com.android.sellacha.api.model.categoriesDM
import com.android.sellacha.databinding.FragmentCreateCategoryBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.Fragment.test.UploadRequestBody
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
    private var type = ""
    private var featured = ""
    private var menu_status = ""
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

        if (CategoryFragment.edit =="2"){
            binding!!.btnSave.text="Update"
            binding!!.txtName.setText(CategoryFragment.cateogoryName)
        }
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
            }
            if(CategoryFragment.edit =="2") {
                uploadImageEdit()
            } else{
                uploadImage()

            }

        }
        categoryList.add(ModelGender("None", -1))
        categoryList.add(ModelGender("services", 0))
        categoryList.add(ModelGender("serviceSchedule", 1))
        categoryList.add(ModelGender("category", 1))
        binding.spinnerCategory.adapter = ArrayAdapter<ModelGender>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            categoryList
        )

        featuredList.add(ModelFeatured("Yes", "1"))
        featuredList.add(ModelFeatured("No", "0"))
        binding.spinnerFeatured.adapter = ArrayAdapter<ModelFeatured>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            featuredList
        )

        assignMenuList.add(ModelFeatured("None", "-1"))
        assignMenuList.add(ModelFeatured("Yes", "1"))
        assignMenuList.add(ModelFeatured("No", "0"))
        binding.spinnerMenu.adapter = ArrayAdapter<ModelFeatured>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            assignMenuList
        )


        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    if (categoryList.size > 0) {
                        type = categoryList[i].gender.toString()
                        Log.e(ContentValues.TAG, "parentCate: $type")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        binding.spinnerFeatured.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    if (featuredList.size > 0) {

                        featured = featuredList[i].value

                        Log.e(ContentValues.TAG, "featured: $featured")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        binding.spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (assignMenuList.size > 0) {

                    menu_status = assignMenuList[i].value.toString()

                    Log.e(ContentValues.TAG, "assignMenu: $menu_status")
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

    }

    private fun uploadImage() {
        if (selectedImageUri == null) {
            binding.layoutRoot.snackbar("Select an Thumbnail First")
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
        Log.e("sessionManager.authToken", sessionManager.authToken.toString())
        Log.e("name", name)
        Log.e("type", type)
        Log.e("featured", featured)
        Log.e("menu_status", menu_status)
        ApiClient.apiService.createCategory(
            sessionManager.authToken,
            name,
            type,
            featured,
            menu_status, MultipartBody.Part.createFormData("file", file.name, body),
        ).enqueue(object : Callback<ModelCreCatogoryJava> {
            override fun onResponse(
                call: Call<ModelCreCatogoryJava>, response: Response<ModelCreCatogoryJava>
            ) {
                try {
                    if (response.code() == 401) {
                        myToast(requireActivity(), "Maximum category limit exceeded")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.code() == 200) {
                        myToast(requireActivity(), response.body()!!.data)
                        Navigation.findNavController(binding!!.root).navigate(R.id.categoryFragment)
                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(requireActivity(), response.body()!!.message)
                        AppProgressBar.hideLoaderDialog()

                    }
                    // apiCallGetPrePending1()

                    //  binding.progressBar.progress = 100

                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                    AppProgressBar.hideLoaderDialog()

                }
            }

            override fun onFailure(call: Call<ModelCreCatogoryJava>, t: Throwable) {
               // binding.layoutRoot.snackbar(t.message!!)
                // binding.progressBar.progress = 0
                myToast(requireActivity(), "Something went wrong")
                AppProgressBar.hideLoaderDialog()

            }

        })
    }
    private fun uploadImageEdit() {
        if (selectedImageUri == null) {
            binding.layoutRoot.snackbar("Select an Thumbnail First")
            return
        }

        val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(
            selectedImageUri!!, "r", null

        ) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        var file = File(
            requireActivity().cacheDir, activity?.contentResolver!!.getFileName(selectedImageUri!!),
        )
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        AppProgressBar.showLoaderDialog(requireContext())

        // binding.progressBar.progress = 0
        val body = UploadRequestBody(file, "image", this)

        val name = binding.txtName.text.toString().trim()
        Log.e("sessionManager.authToken", sessionManager.authToken.toString())
        Log.e("name", name)
        Log.e("type", type)
        Log.e("featured", featured)
        Log.e("menu_status", menu_status)
        ApiClient.apiService.editCategory(
            sessionManager.authToken,
            name,
           MultipartBody.Part.createFormData("file", file.name, body),CategoryFragment.idNew
        ).enqueue(object : Callback<ModelCreCatogoryJava> {
            override fun onResponse(
                call: Call<ModelCreCatogoryJava>, response: Response<ModelCreCatogoryJava>
            ) {
                try {
                    if (response.code() == 401) {
                        myToast(requireActivity(), "Maximum category limit exceeded")
                        AppProgressBar.hideLoaderDialog()

                    } else if (response.code() == 200) {
                        CategoryFragment.edit ="1"
                        myToast(requireActivity(), response.body()!!.data)
                        Navigation.findNavController(binding!!.root).navigate(R.id.categoryFragment)

                        AppProgressBar.hideLoaderDialog()

                    } else {
                        myToast(requireActivity(), response.body()!!.message)
                        AppProgressBar.hideLoaderDialog()

                    }
                    // apiCallGetPrePending1()

                    //  binding.progressBar.progress = 100

                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                    myToast(requireActivity(), "Something went wrong")

                    AppProgressBar.hideLoaderDialog()

                }
            }

            override fun onFailure(call: Call<ModelCreCatogoryJava>, t: Throwable) {
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
                     binding.txtNoFile.text="Thumbnail Selected"
                    binding!!.txtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));
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

    override fun onDestroy() {
        super.onDestroy()
        CategoryFragment.edit ="1"
    }

}


