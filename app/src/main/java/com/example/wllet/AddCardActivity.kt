package com.example.wllet

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddCardActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        database = Firebase.database.reference
        user = intent.getParcelableExtra("user")

        validation()
    }

    private fun luhnMethod(Number: String): Boolean {
        var s1 = 0
        var s2 = 0
        val reverse = StringBuffer(Number).reverse().toString()
        for (i in reverse.indices) {
            val digit = Character.digit(reverse[i], 10)
            when {
                i % 2 == 0 -> s1 += digit
                else -> {
                    s2 += 2 * digit
                    when {
                        digit >= 5 -> s2 -= 9
                    }
                }
            }
        }
        return (s1 + s2) % 10 == 0
    }

    @SuppressLint("SetTextI18n")
    private fun validateCard(Number: String) {
        val button = findViewById<TextView>(R.id.button)
        val editTextCVC = findViewById<TextView>(R.id.editTextCVC)
        val editTextMonthYear = findViewById<TextView>(R.id.editTextMonthYear)
        var cardTypeStatus = "Unsupported"
        val validCheck: String
        if (Number.startsWith("4")) {
            cardTypeStatus =
                if (Number.startsWith("4026") || Number.startsWith("417500") || Number.startsWith("4508") || Number.startsWith(
                        "4844"
                    ) || Number.startsWith("4913") || Number.startsWith("4917")
                ) {
                    "VISA Electron"
                } else {
                    "VISA"
                }
        } else if (Number.startsWith("2") || Number.startsWith("5")) {
            cardTypeStatus = "Mastercard"
        }
        if ((Number.length == 13 || Number.length == 16) && luhnMethod(Number) && editTextCVC.length() == 3 && editTextMonthYear.length() == 4) {
            validCheck = ", Valid"
            button.isEnabled = true
        } else {
            validCheck = ", Invalid card details"
            button.isEnabled = false
        }
        val cardType = findViewById<TextView>(R.id.cardType)
        cardType.text = cardTypeStatus + validCheck
    }

    private fun validation() {
        val editTextCardNumber = findViewById<TextView>(R.id.editTextCardNumber)
        val editTextCVC = findViewById<TextView>(R.id.editTextCVC)
        val editTextMonthYear = findViewById<TextView>(R.id.editTextMonthYear)
        editTextCardNumber.doAfterTextChanged { validateCard(editTextCardNumber.text.toString()) }
        editTextCVC.doAfterTextChanged { validateCard(editTextCardNumber.text.toString()) }
        editTextMonthYear.doAfterTextChanged { validateCard(editTextCardNumber.text.toString()) }
    }

    fun addCard(view: View) {
        val cardNumber = findViewById<EditText>(R.id.editTextCardNumber)
        val cardHolder = findViewById<EditText>(R.id.editTextNameOfCardOwner)
        val cvc = findViewById<EditText>(R.id.editTextCVC)
        val valid = findViewById<EditText>(R.id.editTextMonthYear)

        val newCard = CreditCard(
            cardNumber.text.toString(),
            cardHolder.text.toString(),
            user?.email.toString(),
            cvc.text.toString(),
            valid.text.toString()
        )

        database.child("cards").push().setValue(newCard)
        finish()
    }
}
