package com.android.sellacha.Registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentThemeBinding

class ThemeFragment : Fragment() {
    var binding: FragmentThemeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theme, container, false)
        binding!!.saveBtn.setOnClickListener { view: View? ->

            findNavController(binding!!.getRoot()).navigate(R.id.uploadLogoFragment)
        }
        Log.e("businessName",StoreInformation.RegistrationData.business_name)
        Log.e("mob",StoreInformation.RegistrationData.mob)
        Log.e("shoeType",StoreInformation.RegistrationData.type)
        Log.e("refrral",StoreInformation.RegistrationData.refrral)
        Log.e("email",StoreInformation.RegistrationData.email)
        Log.e("serviceType",StoreInformation.RegistrationData.sertype)
        return binding!!.getRoot()
    }
}