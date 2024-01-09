package com.android.sellacha.setting

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentPaymentOptionsBinding
import com.android.sellacha.sharedpreferences.SessionManager

class PaymentOptionsFragment : Fragment() {
    var binding: FragmentPaymentOptionsBinding? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null
    var url = "#"
    var cOD = false
    var alt = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment_options, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentOptionsBinding.bind(view)
        sessionManager = SessionManager(requireContext())

        with(binding!!) {
            cardInstamojo.setOnClickListener {
                Navigation.findNavController(binding!!.root).navigate(R.id.Instamojo)
            }
            cardPaypal.setOnClickListener {
                Navigation.findNavController(binding!!.root).navigate(R.id.Paypal)
            }

            cardRazorpay.setOnClickListener {
                Navigation.findNavController(binding!!.root).navigate(R.id.Razorpay)
            }

            btnManual.setOnClickListener {
                if (cOD) {
                    cOD = false
                    layoutCOD.visibility = View.GONE

                } else {
                    cOD = true
                    layoutCOD.visibility = View.VISIBLE
                }
            }
            btnAlternative.setOnClickListener {
                if (alt) {
                    alt = false
                    layoutAlternative.visibility = View.GONE

                } else {
                    alt = true
                    layoutAlternative.visibility = View.VISIBLE
                }
            }


        }


    }


}