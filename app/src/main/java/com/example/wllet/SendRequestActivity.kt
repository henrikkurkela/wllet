package com.example.wllet

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SendRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_request)

        val send = findViewById<Button>(R.id.buttonSend)
        val request = findViewById<Button>(R.id.buttonRequest)
        val recipientEmail = findViewById<EditText>(R.id.recipientEmail)
        val amountOfMoney = findViewById<EditText>(R.id.amountOfMoney)

        val sendMoneyDialog = AlertDialog.Builder(this)
            .setTitle("Send")
            .setMessage("Money sent")
            .setPositiveButton("Close") { dialogInterface, _ ->
                Toast.makeText(this, "Money sent successfully", Toast.LENGTH_SHORT).show()
                recipientEmail.setText("")
                amountOfMoney.setText("")
                dialogInterface.dismiss()
            }.create()

        val requestMoneyDialog = AlertDialog.Builder(this)
            .setTitle("Request")
            .setMessage("Money requested")
            .setPositiveButton("Close") { dialogInterface, _ ->
                Toast.makeText(this, "Money requested successfully", Toast.LENGTH_SHORT).show()
                recipientEmail.setText("")
                amountOfMoney.setText("")
                dialogInterface.dismiss()
            }.create()


        send.setOnClickListener {
            sendMoneyDialog.show()
        }

        request.setOnClickListener {
            requestMoneyDialog.show()
        }
    }
}