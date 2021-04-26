package com.example.wllet

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val webView = findViewById<WebView>(R.id.webViewPrivacyPolicy)
        webView.loadUrl("file:///android_asset/PrivacyPolicy.html")
    }
}