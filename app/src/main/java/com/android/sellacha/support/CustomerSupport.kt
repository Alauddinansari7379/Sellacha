package com.android.sellacha.support

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.android.sellacha.databinding.ActivityCustomerSupportBinding


class CustomerSupport : AppCompatActivity() {
    private lateinit var binding:ActivityCustomerSupportBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCustomerSupportBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // binding.webView.webViewClient = WebViewClient()

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        // this will load the url of the website
      //  binding.webView.loadUrl("http://thedemostore.in/mysp.html")
       // binding.webView.loadUrl("https://www.geeksforgeeks.org/android-webview-in-kotlin/")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
       // binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
       // binding.webView.settings.setSupportZoom(true)

        binding.fabChatNow.setOnClickListener {
            val httpIntent = Intent(Intent.ACTION_VIEW)
            httpIntent.data = Uri.parse("http://sellacha.com/mysp.html")
            startActivity(httpIntent)
        }

//        val webSettings = binding.webView.settings
//        webSettings.javaScriptEnabled = true
//       // binding.webView.loadUrl("http://thedemostore.in/mysp.html")
//        binding.webView.loadUrl("http://www.thedemostore.in/make-paymentnew?A4FD127E0358E0D930AB7BAD153AC575A4C4BA3B5ED901AA06904B329441F140FF4B5C4A4588319FE83F0BC0FF50C5D12D0724A52EA0A41F047EABF72DB39DE7D7A6BBEFB4F232EEB2C952A1EC636E964477017E0B79C0B6F89A7AF8E187AC51CA074D94A4A9AB5AA50B2C6B5DA42AD6521FD72FE50CBB5B21AF08602BDF956740969591C4E9CF7F4F5E05230492C17677AD802C6BCAB929D1C0FD02A7F554BEB0FD494D9B6CCCB96F15AA57DC367D4036938DEE8535F988A450ECEF19EBFC02596CEA6B9EAF0305&name=Testnnnre&email=alauddinansar9@gmail.com&id=1")
//         WebView.setWebContentsDebuggingEnabled(false)
//        binding.webView.settings.builtInZoomControls = true
//        binding.webView.settings.displayZoomControls = false
//        binding.webView.settings.javaScriptEnabled = true
        
    }
}