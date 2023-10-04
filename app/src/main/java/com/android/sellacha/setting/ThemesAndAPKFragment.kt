package com.android.sellacha.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.sellacha.OfferAndAds.model.ModelCreateAd
import com.android.sellacha.R
import com.android.sellacha.databinding.FragmentCreateBannerAdBinding
import com.android.sellacha.databinding.FragmentCreateBumpAdBinding
import com.android.sellacha.databinding.FragmentDomainSettingBinding
import com.android.sellacha.databinding.FragmentPaymentOptionsBinding
import com.android.sellacha.databinding.FragmentThemesApkBinding
import com.android.sellacha.helper.myToast
import com.android.sellacha.utils.AppProgressBar
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.myrecyview.apiclient.ApiClient
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ThemesAndAPKFragment : Fragment(){
    var binding: FragmentThemesApkBinding? = null
    private lateinit var sessionManager: SessionManager
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_themes_apk, container, false)
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThemesApkBinding.bind(view)
        sessionManager = SessionManager(requireContext())



    }



}