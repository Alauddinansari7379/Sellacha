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
import com.android.sellacha.databinding.FragmentSellerGoogleTagManagerBinding

class SellerGoogleTagManagerFragment : Fragment() {
    var binding: FragmentSellerGoogleTagManagerBinding? = null
    var statuseList = ArrayList<ModelProductType>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_seller_google_tag_manager,
            container,
            false
        )



        binding!!.txtGoogleManId.setText(StoreInformation.RegistrationData.tag_id)
        binding!!.txtSatus.setSelection(StoreInformation.RegistrationData.astatusValue )

        binding!!.saveBtn.setOnClickListener { view: View? ->
            if (binding!!.txtGoogleManId.text.isEmpty()) {
                binding!!.txtGoogleManId.error = "Enter Google Tag Manager ID"
                binding!!.txtGoogleManId.requestFocus()
                return@setOnClickListener
            }
            StoreInformation.RegistrationData.tag_id = binding!!.txtGoogleManId.text.toString()
            findNavController(binding!!.root).navigate(R.id.facebookPixelFragment)
        }

        binding!!.skipLb.setOnClickListener { view: View? ->
            StoreInformation.RegistrationData.tag_id=binding!!.txtGoogleManId.text.toString()

            findNavController(binding!!.root).navigate(R.id.facebookPixelFragment)
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
                        StoreInformation.RegistrationData.astatus = statuseList[i].text
                        StoreInformation.RegistrationData.astatusValue = statuseList[i].value


                        //  Log.e(ContentValues.TAG, "statuse: $statuse")
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }


        return binding!!.root
    }
}