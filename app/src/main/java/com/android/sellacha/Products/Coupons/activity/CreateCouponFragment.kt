package com.android.sellacha.Products.Coupons.activity

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
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.Products.Coupons.MOdel.ModelCreateCoupon
import com.android.sellacha.Products.Coupons.activity.CouponsFragment.Companion.edit
import com.android.sellacha.Products.Coupons.activity.CouponsFragment.Companion.idNew
import com.android.sellacha.R
import com.android.sellacha.api.model.reviewRatingDM
import com.android.sellacha.databinding.FragmentCreateCouponBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.android.sellacha.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreateCouponFragment : Fragment() {
    var binding: FragmentCreateCouponBinding? = null
    var reviewRatingArr = ArrayList<reviewRatingDM>()
    private lateinit var sessionManager: SessionManager
    var mydilaog: Dialog? = null
    var expiredDate=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_coupon, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateCouponBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        if (edit=="2"){
            binding!!.SignInBtn.text="Update"
            binding!!.location1.setText(CouponsFragment.couponCode)
            binding!!.txtExpiredDate.text = CouponsFragment.exDate
        }

        binding!!.SignInBtn.setOnClickListener {
            if (binding!!.location1.text.isEmpty()){
                binding!!.location1.error="Enter Coupon"
                binding!!.location1.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.txtExpiredDate.text.isEmpty()){
                binding!!.txtExpiredDate.error="Select Expired Date"
                 return@setOnClickListener
            }
            if (binding!!.txtPercentage.text.isEmpty()){
                binding!!.txtPercentage.error="Enter Percentage"
                binding!!.txtPercentage.requestFocus()
                return@setOnClickListener
            }
            if (edit=="2"){
                apiCallEdit()
            }else{
                apiCallCreateCoupons()

            }
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
                expiredDate= SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate.time)
                binding!!.txtExpiredDate.text = expiredDate
                 Log.e(ContentValues.TAG, "selectedate:>>$expiredDate")
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000;

        binding!!.txtExpiredDate.setOnClickListener {
            datePicker.show()
        }

    }

    private fun apiCallCreateCoupons() {
        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.createCoupon(sessionManager.authToken,binding!!.location1.text.toString().trim(),binding!!.txtPercentage.text.toString().trim(),binding!!.txtExpiredDate.text.toString().trim())
            .enqueue(object : Callback<ModelCreateCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateCoupon>, response: Response<ModelCreateCoupon>
                ) {
                    try {

                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            myToast(requireActivity(), "Something went wrong")
                             AppProgressBar.hideLoaderDialog()

                          } else if (response.code() == 401) {
                            myToast(requireActivity(), "Maximum posts limit exceeded")
                             AppProgressBar.hideLoaderDialog()

                        } else {
                            myToast(requireActivity(), response.body()!!.data)
                            Navigation.findNavController(binding!!.root).navigate(R.id.couponFragment)
                            AppProgressBar.hideLoaderDialog()
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelCreateCoupon>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Something went wrong")
                    apiCallCreateCoupons()
                }

            })
    }
    private fun apiCallEdit() {

        AppProgressBar.showLoaderDialog(requireContext())

        ApiClient.apiService.editCoupon(sessionManager.authToken,
            binding!!.location1.text.toString().trim(),
            binding!!.txtPercentage.text.toString().trim(),
            binding!!.txtExpiredDate.text.toString().trim(),
            idNew)
            .enqueue(object : Callback<ModelCreateCoupon> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelCreateCoupon>, response: Response<ModelCreateCoupon>
                ) {
                    try {

                        if (response.code() == 500) {
                            myToast(requireActivity(), "Server Error")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 404) {
                            myToast(requireActivity(), "Something went wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else if (response.code() == 401) {
                            myToast(requireActivity(), "Something went wrong")
                            AppProgressBar.hideLoaderDialog()

                        } else {
                            myToast(requireActivity(), response.body()!!.data)
                             edit="1"
                            Navigation.findNavController(binding!!.root).navigate(R.id.couponFragment)
                            AppProgressBar.hideLoaderDialog()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        AppProgressBar.hideLoaderDialog()

                    }
                }

                override fun onFailure(call: Call<ModelCreateCoupon>, t: Throwable) {
                    // myToast(requireActivity(),t.message.toString())
                    // myToast(requireActivity(), "Try Again")
                    AppProgressBar.hideLoaderDialog()
                     apiCallEdit()
                }

            })

    }


    override fun onDestroy() {
        super.onDestroy()
        edit ="1"
    }
}