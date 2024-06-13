package com.android.sellacha.Products.createProduct.Model

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.android.sellacha.OfferAndAds.activity.CreateBannerAdFragment
import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.Registration.StoreInformation
import com.android.sellacha.databinding.FragmentCreateProductBinding
import com.android.sellacha.helper.myToast
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreateProductFragment : Fragment(), UploadRequestBody.UploadCallback {
    var binding: FragmentCreateProductBinding? = null
    var priceTypeList = ArrayList<ModelProductType>()
    var stockStatues = ArrayList<ModelProductType>()
    lateinit var sessionManager: SessionManager
    var mydilaog: Dialog? = null
    var priceType = ""
    var specialPrice = ""
    var price = ""
    var title = ""
    var startdate = ""
    var endtdate = ""
    var manageStock = ""
    var stockS = ""
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_product, container, false)
        sessionManager = SessionManager(requireContext())


        priceTypeList.add(ModelProductType("Fixed", 1))
        priceTypeList.add(ModelProductType("Percentage", 2))


        stockStatues.add(ModelProductType("In Stock", 1))
        stockStatues.add(ModelProductType("Out of Stock", 0))

        binding!!.spinnerMenu!!.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            priceTypeList
        )
        binding!!.spinnerMenu.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            priceTypeList
        )

        binding!!.spinnerStoctstuate.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            stockStatues
        )

        binding!!.btnchoosefile.setOnClickListener {
            openImageChooser()
        }

        mydilaog?.setCanceledOnTouchOutside(false)
        mydilaog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val newCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                DateFormat.getDateInstance().format(newDate.time)
                // val Date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(newDate.time)
                startdate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(newDate.time)
                val selectedDate =
                    SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(newDate.time)

                binding!!.location.text = startdate
                StoreInformation.RegistrationData.special_price_start = startdate

                Log.e(ContentValues.TAG, "selectedate:>>$startdate")
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000;

        binding!!.location.setOnClickListener {
            datePicker.show()
        }

        mydilaog?.setCanceledOnTouchOutside(false)
        mydilaog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val newCalendar1 = Calendar.getInstance()
        val datePicker1 = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                DateFormat.getDateInstance().format(newDate.time)
                // val Date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(newDate.time)
                endtdate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(newDate.time)
                val selectedDate =
                    SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(newDate.time)

                binding!!.txtSpecialPriceEnd.text = endtdate
                StoreInformation.RegistrationData.special_price_end = endtdate

                Log.e(ContentValues.TAG, "selectedate:>>$endtdate")
            },
            newCalendar1[Calendar.YEAR],
            newCalendar1[Calendar.MONTH],
            newCalendar1[Calendar.DAY_OF_MONTH]
        )
        datePicker1.datePicker.minDate = System.currentTimeMillis() - 1000;

        binding!!.txtSpecialPriceEnd.setOnClickListener {
            datePicker1.show()
        }

        binding!!.spinnerMenu.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (priceTypeList.size > 0) {
                        priceType = priceTypeList[i].text

                        Log.e(ContentValues.TAG, "priceType: $priceType")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

        binding!!.spinnerStoctstuate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (stockStatues.size > 0) {
                        stockS = stockStatues[i].value.toString()

                        Log.e(ContentValues.TAG, "stockS: $stockS")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }


        binding!!.SignInBtn.setOnClickListener {
            if (binding!!.txtproductTitle.text.isEmpty()) {
                binding!!.txtproductTitle.error = "Enter Product Title"
                binding!!.txtproductTitle.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.txtPrice.text.isEmpty()) {
                binding!!.txtPrice.error = "Enter Price"
                binding!!.txtPrice.requestFocus()
                return@setOnClickListener
            }
//            if (binding!!.txtSpecialPrice.text.isEmpty()) {
//                binding!!.txtSpecialPrice.error = "Enter Special Price"
//                binding!!.txtSpecialPrice.requestFocus()
//                return@setOnClickListener
//            }
//            if (binding!!.location.text.isEmpty()) {
//                binding!!.location.error = "Select Special Price Start Date"
//                binding!!.location.requestFocus()
//                return@setOnClickListener
//            }
//            if (binding!!.txtSpecialPriceEnd.text.isEmpty()) {
//                binding!!.txtSpecialPriceEnd.error = "Select Special Price End Date"
//                binding!!.txtSpecialPriceEnd.requestFocus()
//                return@setOnClickListener
//            }

            if (binding!!.switch1.isChecked){
                if (binding!!.txtStockQuantity.text.isEmpty()) {
                    binding!!.txtStockQuantity.error = "Enter Stock Qty"
                    binding!!.txtStockQuantity.requestFocus()
                    return@setOnClickListener
                }  
            }


            if (binding!!.txtSku.text.isEmpty()) {
                binding!!.txtSku.error = "Enter Sku"
                binding!!.txtSku.requestFocus()
                return@setOnClickListener
            }
            manageStock = if (binding!!.switch1.isEnabled) {
                "1"
            } else {
                "0"

            }

            title = binding!!.txtproductTitle.text.toString()
            price = binding!!.txtPrice.text.toString()
            specialPrice = binding!!.txtSpecialPrice.text.toString()

            apiCreateProduct()

        }
        return binding!!.root
    }

    private fun apiCreateProduct() {
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

        ApiClient.apiService.createProduct(
            sessionManager.authToken,
            title,
            price,
            specialPrice,
            priceType,
            startdate,
            endtdate,
            "product",
            manageStock,
            binding!!.txtStockQuantity.text.toString().trim(),
            binding!!.txtSku.text.toString().trim(),
            stockS,
            MultipartBody.Part.createFormData("media", file.name, body)
        )
            .enqueue(object : Callback<ModelCreatePro> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreatePro>, response: Response<ModelCreatePro>
                ) {

                    try {
                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 200) {
//                            if (response.body()!!.message.contains("limit")) {
//                                myToast(requireActivity(), "Limit Exceeded")
//                                AppProgressBar.hideLoaderDialog()
//
//                            }
                            if (response.body()!!.data.product_id != null) {
                                myToast(requireActivity(), "Product Created")
                                binding!!.txtproductTitle.text.clear()
                                binding!!.txtPrice.text.clear()
                                binding!!.txtSpecialPrice.text.clear()
                                binding!!.txtSku.text.clear()
                                binding!!.txtStockQuantity.text.clear()
                                binding!!.txtSpecialPriceEnd.text = ""
                                binding!!.location.text = ""
                                AppProgressBar.hideLoaderDialog()
                            } else {
                                myToast(requireActivity(), response.body()!!.message)
                                AppProgressBar.hideLoaderDialog()

                            }

                        } else {
                            myToast(requireActivity(), response.body()!!.message)
                            AppProgressBar.hideLoaderDialog()


                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        myToast(requireActivity(), "Something went wrong")
                        AppProgressBar.hideLoaderDialog()
                    }
                }

                override fun onFailure(call: Call<ModelCreatePro>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            (MediaStore.ACTION_IMAGE_CAPTURE)
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, CreateBannerAdFragment.REQUEST_CODE_IMAGE)
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
                    binding?.imgView?.setImageURI(selectedImageUri)
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