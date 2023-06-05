package com.android.sellacha.OfferAndAds

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.Products.Attributes.activity.MOdel.ModelAttributes
import com.android.sellacha.Products.Attributes.adapter.AdapterAttribute
import com.android.sellacha.Products.categories.Model.ModelCategory
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentAttributeBinding
import com.android.sellacha.databinding.FragmentBumpAdsBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BumpAdsFragment : Fragment() {
    var binding: FragmentBumpAdsBinding? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bump_ads, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBumpAdsBinding.bind(view)
        sessionManager = SessionManager(requireContext())



        CoroutineScope(Dispatchers.IO).launch {
            Log.d("FetchContact89", "fetchContacts: coroutine start")


        }
    }


}