package com.android.sellacha.setting

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentDomainSettingBinding
import com.android.sellacha.sharedpreferences.SessionManager

class DomainSettingFragment : Fragment(){
    var binding: FragmentDomainSettingBinding? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null
    var url="#"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_domain_setting, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDomainSettingBinding.bind(view)

        sessionManager = SessionManager(requireContext())

         binding!!.btnchoosefile.setOnClickListener {
         }

        binding!!.saveBtn.setOnClickListener {
         }

    }



}