package com.example.wllet

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_terms_and_conditions.*

class TermsAndConditionsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_terms_and_conditions)
        webViewTermsAndConditions.loadUrl("file:///android_asset/TermsAndConditions.html")
    }
}