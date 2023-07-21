package com.android.sellacha.Registration

import android.content.ContentValues
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
import com.android.sellacha.databinding.FragmentSellerGoogleAnalyticsBinding

class SellerGoogleAnalyticsFragment : Fragment() {
    var binding: FragmentSellerGoogleAnalyticsBinding? = null
    var statuseList = ArrayList<ModelProductType>()
    var statuse=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_seller_google_analytics,
            container,
            false
        )
        binding!!.txtMeasurmentId.setText(StoreInformation.RegistrationData.ga_measurement_id)
        binding!!.txtAnalyticId.setText(StoreInformation.RegistrationData.analytics_view_id)
        binding!!.txtSatus.setSelection(StoreInformation.RegistrationData.tstatusValue)




        binding!!.saveBtn.setOnClickListener { view: View? ->
            if (binding!!.txtMeasurmentId.text.isEmpty()) {
                binding!!.txtMeasurmentId.error = "Enter GA Measurement-ID"
                binding!!.txtMeasurmentId.requestFocus()
                return@setOnClickListener
            }
            if (binding!!.txtAnalyticId.text.isEmpty()) {
                binding!!.txtAnalyticId.error = "Enter Analytics View ID"
                binding!!.txtAnalyticId.requestFocus()
                return@setOnClickListener
            }
            StoreInformation.RegistrationData.ga_measurement_id=binding!!.txtMeasurmentId.text.toString()
            StoreInformation.RegistrationData.analytics_view_id=binding!!.txtAnalyticId.text.toString()
            findNavController(binding!!.root).navigate(R.id.tapManagerFragment)
        }

        binding!!.skipLb.setOnClickListener { view: View? ->
            findNavController(binding!!.root).navigate(R.id.tapManagerFragment)
        }


        statuseList.add(ModelProductType("Enable", 1))
        statuseList.add(ModelProductType("Disable", 2))

        binding!!.txtSatus.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            statuseList
        )


        binding!!.txtSatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (statuseList.size > 0) {
                        StoreInformation.RegistrationData.tstatus = statuseList[i].text
                        StoreInformation.RegistrationData.tstatusValue = statuseList[i].value


                        Log.e(ContentValues.TAG, "statuse: $statuse")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

        return binding!!.root
    }
}