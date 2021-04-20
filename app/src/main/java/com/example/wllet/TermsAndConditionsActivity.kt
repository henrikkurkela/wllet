package com.example.wllet

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class TermsAndConditionsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)

        val webView = findViewById<WebView>(R.id.webViewTermsAndConditions)
        webView.loadUrl("file:///android_asset/TermsAndConditions.html")
    }
}