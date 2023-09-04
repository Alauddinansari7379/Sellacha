package com.android.sellacha.Registration

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.sellacha.LogIn.LoginActivity
import com.android.sellacha.databinding.ActivityRegistrationSucessBinding

class RegistrationSucess : AppCompatActivity() {
    private lateinit var binding:ActivityRegistrationSucessBinding
    var domain=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationSucessBinding.inflate(layoutInflater)
        setContentView(binding.root)
       domain= intent.getStringExtra("domain").toString()
        binding.edtURL.setText(domain)

        binding.imgBack.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)        }

        binding.btnSignIn.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)
        }
        binding.tvSignBrowser.setOnClickListener {
            val browse = Intent(Intent.ACTION_VIEW, Uri.parse("$domain"))
            startActivity(browse)
        }
    }
}