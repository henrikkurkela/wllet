package com.example.wllet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class WalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
    }
    fun addcard(view: View) {
        view.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            view.context.startActivity(intent)
        }
    }
}
