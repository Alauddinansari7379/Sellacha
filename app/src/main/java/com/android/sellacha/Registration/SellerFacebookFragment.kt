package com.android.sellacha.Registration

import android.os.Bundle
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
import com.android.sellacha.databinding.FragmentSellerFacebookBinding

class SellerFacebookFragment : Fragment() {
    var binding: FragmentSellerFacebookBinding? = null
    var statuseList = ArrayList<ModelProductType>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_seller_facebook, container, false)



        binding!!.txtYourPixelID.setText(StoreInformation.RegistrationData.pixel_id)
        binding!!.txtStatus.setSelection(StoreInformation.RegistrationData.pstatusValue )

        binding!!.saveBtn.setOnClickListener { view: View? ->
            if (binding!!.txtYourPixelID.text.isEmpty()) {
                binding!!.txtYourPixelID.error = "Enter Pixel ID"
                binding!!.txtYourPixelID.requestFocus()
                return@setOnClickListener
            }
            StoreInformation.RegistrationData.pixel_id=binding!!.txtYourPixelID.text.toString()

            findNavController(binding!!.root).navigate(R.id.whatsappApiFragment)

        }

        binding!!.skipLb.setOnClickListener { view: View? ->
            StoreInformation.RegistrationData.pixel_id=binding!!.txtYourPixelID.text.toString()
            findNavController(binding!!.root).navigate(R.id.whatsappApiFragment)

        }


        statuseList.add(ModelProductType("Enable", 1))
        statuseList.add(ModelProductType("Disable", 2))

        binding!!.txtStatus.adapter = ArrayAdapter<ModelProductType>(
            requireContext(),
            R.layout.simple_list_item_1,
            statuseList
        )


        binding!!.txtStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    if (statuseList.size > 0) {
                        StoreInformation.RegistrationData.pstatus = statuseList[i].text
                        StoreInformation.RegistrationData.pstatusValue = statuseList[i].value


                        //  Log.e(ContentValues.TAG, "statuse: $statuse")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

        return binding!!.root
    }
}