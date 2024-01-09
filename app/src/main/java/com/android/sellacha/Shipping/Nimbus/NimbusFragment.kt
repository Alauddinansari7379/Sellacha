package com.android.sellacha.Shipping.Nimbus

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentNimbusBinding
import com.android.sellacha.sharedpreferences.SessionManager


class NimbusFragment : Fragment() {
    lateinit var binding: FragmentNimbusBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nimbus, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNimbusBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        binding.signUp.setOnClickListener {
            val browse = Intent(Intent.ACTION_VIEW, Uri.parse("https://ship.nimbuspost.com/users/signup"))
            startActivity(browse)
        }


    }
}
