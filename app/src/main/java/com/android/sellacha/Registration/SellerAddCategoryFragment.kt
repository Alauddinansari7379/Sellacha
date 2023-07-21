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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.databinding.FragmentSellerAddCategoryBinding
import com.android.sellacha.helper.myToast
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class SellerAddCategoryFragment : Fragment(), UploadRequestBody.UploadCallback {
    var binding: FragmentSellerAddCategoryBinding? = null
    var parentCategoryList = ArrayList<ModelProductType>()
    var featureList = ArrayList<ModelProductType>()
    var assignMenuList = ArrayList<ModelProductType>()
    var parentCategory = ""
    var feature = ""
    private var thumbnail: Uri? = null

    var assignMenu = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_seller_add_category,
            container,
            false
        )

        if(StoreInformation.RegistrationData.thumbnail!=null){
            binding!!.txtNoFile.text = "Thumbnail Selected"
            binding!!.txtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));
            binding!!.txtName.setText(StoreInformation.RegistrationData.title)
            binding!!.spinnerCategory.setSelection(StoreInformation.RegistrationData.cnameValue)
            binding!!.spinnerFeatured.setSelection(StoreInformation.RegistrationData.featuredValue)
            binding!!.spinnerMenu.setSelection(StoreInformation.RegistrationData.menu_statusValue)
        }



        binding!!.saveBtn.setOnClickListener { view: View? ->
            if (binding!!.txtName.text.isEmpty()) {
                binding!!.txtName.error = "Enter Title"
                binding!!.txtName.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.spinnerCategory.selectedItemPosition==0) {
                myToast(requireActivity(),"Select Parent Category")
                return@setOnClickListener
            }

            StoreInformation.RegistrationData.title = binding!!.txtName.text.toString()
            uploadImage()
           // findNavController(binding!!.root).navigate(R.id.addProduct2Fragment)

        }
//
//        binding!!.skipLb.setOnClickListener {
//            findNavController(binding!!.root).navigate(R.id.addProduct2Fragment)
//
//        }
        binding!!.btnchoosefile.setOnClickListener {
            opeinImageChooser()

        }

        parentCategoryList.add(ModelProductType("None", 0))
        parentCategoryList.add(ModelProductType("Dairy", 1))
        parentCategoryList.add(ModelProductType("Health", 1))
        parentCategoryList.add(ModelProductType("Fruit", 1))
        parentCategoryList.add(ModelProductType("Vegetable", 1))
        parentCategoryList.add(ModelProductType("Clothing", 1))
        parentCategoryList.add(ModelProductType("Hand Bags", 1))
        parentCategoryList.add(ModelProductType("Hijab Wear", 1))
        parentCategoryList.add(ModelProductType("Purses", 1))
        parentCategoryList.add(ModelProductType("Shoes", 1))
        parentCategoryList.add(ModelProductType("Cosmetics", 1))

        binding!!.spinnerCategory.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            parentCategoryList
        )
        binding!!.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (parentCategoryList.size > 0) {
                        StoreInformation.RegistrationData.cname = parentCategoryList[i].text
                        StoreInformation.RegistrationData.cnameValue = parentCategoryList[i].value

                        Log.e(ContentValues.TAG, "parentCategory: $parentCategory")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

        featureList.add(ModelProductType("No", 1))
        featureList.add(ModelProductType("Yes", 2))

        binding!!.spinnerFeatured.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            featureList
        )

        assignMenuList.add(ModelProductType("No", 1))
        assignMenuList.add(ModelProductType("Yes", 2))
        binding!!.spinnerMenu.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            assignMenuList
        )

        binding!!.spinnerFeatured.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (featureList.size > 0) {
                        StoreInformation.RegistrationData.featured = featureList[i].text
                        StoreInformation.RegistrationData.featuredValue = featureList[i].value

                        Log.e(ContentValues.TAG, "feature: $feature")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        binding!!.spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                if (assignMenuList.size > 0) {
                    StoreInformation.RegistrationData.menu_status = assignMenuList[i].text
                    StoreInformation.RegistrationData.menu_statusValue = assignMenuList[i].value

                    Log.e(ContentValues.TAG, "menu_status: ${StoreInformation.RegistrationData.menu_status}")
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }




        return binding!!.root
    }
    private fun uploadImage() {
        if (StoreInformation.RegistrationData.thumbnail == null) {
            myToast(requireActivity(),"Select Thumbnail")
            return
        }
        val parcelFileDescriptorLogo =
            activity?.contentResolver?.openFileDescriptor(StoreInformation.RegistrationData.thumbnail!!, "r", null)
                ?: return


        val inputStreamLogo = FileInputStream(parcelFileDescriptorLogo.fileDescriptor)
        var thumbnail = File(requireActivity().cacheDir, activity?.contentResolver!!.getFileName(StoreInformation.RegistrationData.thumbnail!!))


        val outputStreamLogo = FileOutputStream(thumbnail)
        inputStreamLogo.copyTo(outputStreamLogo)



       // StoreInformation.RegistrationData.file= thumbnail.toString()

        findNavController(binding!!.root).navigate(R.id.addProduct2Fragment)

        val body = UploadRequestBody(thumbnail, "image", this)
        MultipartBody.Part.createFormData("image", thumbnail.name, body)
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
                    StoreInformation.RegistrationData.thumbnail=data?.data
                    binding!!.txtNoFile.text = "Thumbnail Selected"
                    binding!!.txtNoFile.setTextColor(Color.parseColor("#FF4CAF50"));
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