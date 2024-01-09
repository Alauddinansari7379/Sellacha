package com.android.sellacha.Upgrade

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.sellacha.databinding.FragmentUpgradePlanBinding
import com.android.sellacha.helper.myToast


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
          //  activity?.let { it1 -> myToast(it1,"Work On Progress") }
        }
        return binding!!.root

    }




}