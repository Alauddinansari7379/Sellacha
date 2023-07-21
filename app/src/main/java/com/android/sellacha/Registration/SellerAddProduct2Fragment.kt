package com.android.sellacha.Registration

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
import androidx.navigation.Navigation.findNavController
import com.android.sellacha.R
import com.android.sellacha.Registration.Model.ModelProductType
import com.android.sellacha.databinding.FragmentSellerAddProduct2Binding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SellerAddProduct2Fragment : Fragment() {
    var binding: FragmentSellerAddProduct2Binding? = null
    var mydilaog: Dialog? = null
    var priceTypeList = ArrayList<ModelProductType>()
    var priceType = ""
    var startdate = ""
    var endtdate = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_seller_add_product2,
            container,
            false
        )

        binding!!.txtproductTitle.setText(StoreInformation.RegistrationData.productTitle)
        binding!!.txtPrice.setText(StoreInformation.RegistrationData.price)
        binding!!.txtSpecialPrice.setText(StoreInformation.RegistrationData.special_price)
        binding!!.location.setText(StoreInformation.RegistrationData.special_price_start)
        binding!!.txtSpecialPriceEnd.setText(StoreInformation.RegistrationData.special_price_end)
        binding!!.spinnerMenu.setSelection(StoreInformation.RegistrationData.price_typeValue )


        binding!!.saveBtn.setOnClickListener { view: View? ->
            if (binding!!.txtproductTitle.text.isEmpty()) {
                binding!!.txtproductTitle.error = "Enter Product Title"
                binding!!.txtproductTitle.requestFocus()
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
            StoreInformation.RegistrationData.productTitle = binding!!.txtproductTitle.text.toString()
            StoreInformation.RegistrationData.price = binding!!.txtPrice.text.toString()
            StoreInformation.RegistrationData.special_price = binding!!.txtSpecialPrice.text.toString()
            findNavController(binding!!.root).navigate(R.id.googleAnalyticsFragment)

        }

//        binding!!.skipLb.setOnClickListener { view: View? ->
//            findNavController(binding!!.root).navigate(R.id.googleAnalyticsFragment)
//        }
        priceTypeList.add(ModelProductType("Fixed", 1))
        priceTypeList.add(ModelProductType("Percentage", 2))

        binding!!.spinnerMenu.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            priceTypeList
        )
        binding!!.spinnerMenu.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            priceTypeList
        )

        binding!!.spinnerMenu.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (priceTypeList.size > 0) {
                        StoreInformation.RegistrationData.price_type = priceTypeList[i].text
                        StoreInformation.RegistrationData.price_typeValue = priceTypeList[i].value

                        Log.e(ContentValues.TAG, "priceType: $priceType")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
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
                val selectedDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(newDate.time)
                binding!!.location.text = startdate
                StoreInformation.RegistrationData.special_price_start =startdate
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
                val selectedDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(newDate.time)

                binding!!.txtSpecialPriceEnd.text = endtdate
                StoreInformation.RegistrationData.special_price_end =endtdate

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



        return binding!!.root
    }
}