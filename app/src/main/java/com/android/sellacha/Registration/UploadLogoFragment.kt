package com.android.sellacha.Registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentUploadLogoBinding

class UploadLogoFragment : Fragment() {
    var binding: FragmentUploadLogoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload_logo, container, false)

        binding!!.txtTagline.setText(StoreInformation.RegistrationData.tag_idName)
        binding!!.txtName.setText(StoreInformation.RegistrationData.name)

        binding!!.saveBtn.setOnClickListener { view: View? ->

            if (binding!!.txtName.text.isEmpty()) {
                binding!!.txtName.error = "Enter Brand Name"
                binding!!.txtName.requestFocus()
                return@setOnClickListener
            }

            if (binding!!.txtTagline.text.isEmpty()) {
                binding!!.txtTagline.error = "Enter Tagline"
                binding!!.txtTagline.requestFocus()
                return@setOnClickListener
            }
            StoreInformation.RegistrationData.tag_idName = binding!!.txtTagline.text.toString()
            StoreInformation.RegistrationData.name = binding!!.txtName.text.toString().trim()
            findNavController(binding!!.getRoot()).navigate(R.id.colorSchemeFragment)
        }



        return binding!!.root
    }
}