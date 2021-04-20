package com.example.wllet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_subscription.*

class SubscriptionActivity: AppCompatActivity() {
    /*companion object {
        const val name = "NAME"
        const val status = "STATUS"
        const val price = "AGE"
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        val intent = intent
        val aTitle = intent.getStringExtra("iTitle")
        val aPrice = intent.getStringExtra("iPrice")
        val aImageView = intent.getIntExtra("iImageView", 0)

        a_title.text = aTitle
        a_description.text = aPrice
        imageView.setImageResource(aImageView)
    }
}