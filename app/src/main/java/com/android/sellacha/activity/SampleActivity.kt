package com.android.sellacha.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.sellacha.R
import com.android.sellacha.databinding.ActivitySampleBinding
import com.android.sellacha.helper.CbnMenuItem

class SampleActivity : BaseActivity() {
    lateinit var binding: ActivitySampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample)

        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_notification,
                R.drawable.ic_baseline_home_24,

            ),
            CbnMenuItem(
                R.drawable.ic_dashboard,
                R.drawable.notes_icn,

            ),
            CbnMenuItem(
                R.drawable.ic_home,
                R.drawable.notes_icn,

            ),
            CbnMenuItem(
                R.drawable.ic_profile,
                R.drawable.notes_icn,

            ),
            CbnMenuItem(
                R.drawable.ic_settings,
                R.drawable.ic_baseline_home_24,

            )
        )
       // binding.navView.setMenuItems(menuItems, 2)

    }
}