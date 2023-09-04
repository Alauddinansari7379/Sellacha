package com.android.sellacha.Upgrade

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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.android.sellacha.databinding.FragmentColorSchemeBinding
import com.android.sellacha.databinding.FragmentUpgradePlanBinding
import com.android.sellacha.helper.myToast
import com.example.ehcf.Fragment.test.UploadRequestBody
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class UpgradePlanFragment : Fragment(){
    var binding: FragmentUpgradePlanBinding? = null
    private var image=0
    var urlList = ArrayList<String>()
    var iconClassList = ArrayList<String>()
    private var logo: Uri? = null
    private var favicon: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, com.android.sellacha.R.layout.fragment_upgrade_plan, container, false)


        binding!!.btnUpgrade.setOnClickListener {
            activity?.let { it1 -> myToast(it1,"Work On Progress") }
        }
        return binding!!.root

    }




}