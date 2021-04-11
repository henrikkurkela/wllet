package com.example.wllet

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class PrivacyPolicyActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_privacy_policy)
        webViewPrivacyPolicy.loadUrl("file:///android_asset/PrivacyPolicy.html")
    }
}