package com.android.sellacha.Products

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.sellacha.Products.Coupons.MOdel.ModelCoupons
import com.android.sellacha.Products.Coupons.adapter.AdapterCoupons
import com.android.sellacha.Products.createProduct.Model.ModelCreatePro
import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.Registration.StoreInformation
import com.android.sellacha.databinding.FragmentCreateProductBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreateProductFragment : Fragment() {
    var binding: FragmentCreateProductBinding? = null
    var priceTypeList = ArrayList<ModelProductType>()
    lateinit var sessionManager: SessionManager
    var mydilaog: Dialog? = null
    var priceType = ""
    var specialPrice = ""
    var price = ""
    var title = ""
    var startdate = ""
    var endtdate = ""
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
            if (binding!!.txtSpecialPrice.text.isEmpty()) {
                binding!!.txtSpecialPrice.error = "Enter Special Price"
                binding!!.txtSpecialPrice.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.location.text.isEmpty()) {
                binding!!.location.error = "Select Special Price Start Date"
                binding!!.location.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.txtSpecialPriceEnd.text.isEmpty()) {
                binding!!.txtSpecialPriceEnd.error = "Select Special Price End Date"
                binding!!.txtSpecialPriceEnd.requestFocus()
                return@setOnClickListener
            }

            title = binding!!.txtproductTitle.text.toString()
            price = binding!!.txtPrice.text.toString()
            specialPrice = binding!!.txtSpecialPrice.text.toString()

            apiCreateProduct()

        }
        return binding!!.root
    }

    private fun apiCreateProduct() {


        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.createProduct(
            sessionManager.authToken,
            title,
            price,
            specialPrice,
            priceType,
            startdate,
            endtdate,
            "product"
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
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ModelCreatePro>, t: Throwable) {
                    myToast(requireActivity(), "Something went wrong")
                    AppProgressBar.hideLoaderDialog()


                }

            })
    }


}