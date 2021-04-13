package com.example.wllet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.activity_add_card.*

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        validation()
    }
    fun luhnmethod(Number: String): Boolean {
        var s1 = 0
        var s2 = 0
        val reverse = StringBuffer(Number).reverse().toString()
        for (i in reverse.indices) {
            val digit = Character.digit(reverse[i], 10)
            when {
                i % 2 == 0 -> s1 += digit
                else -> {
                    s2 += 2 * digit
                    when {digit >= 5 -> s2 -= 9}
                }
            }
        }
        return (s1 + s2) % 10 == 0
    }
    fun validatecard (Number: String) {
        var button = findViewById<TextView>(R.id.button)
        var editTextCVC = findViewById<TextView>(R.id.editTextCVC)
        var editTextMonthYear = findViewById<TextView>(R.id.editTextMonthYear)
        var cardtype = "Unsupported"
        var validcheck = ""
        if (Number.startsWith("4")) {
            if (Number.startsWith("4026") || Number.startsWith("417500") || Number.startsWith("4508") || Number.startsWith("4844") || Number.startsWith("4913") || Number.startsWith("4917"))
            {
                cardtype = "VISA Electron"
            } else {
                cardtype = "VISA"
            }
        } else if (Number.startsWith("2") || Number.startsWith("5")) {
                cardtype = "Mastercard"
        }
        if ((Number.length == 13 || Number.length == 16) && luhnmethod(Number) && editTextCVC.length() == 3 && editTextMonthYear.length() == 4){
            validcheck = ", Valid"
            button.isEnabled = true
        } else {
            validcheck = ", Invalid card details"
            button.isEnabled = false
        }
        var cardType = findViewById<TextView>(R.id.cardType)
        cardType.text = cardtype + validcheck
    }
    fun validation () {
        var editTextCardNumber = findViewById<TextView>(R.id.editTextCardNumber)
        editTextCardNumber.doAfterTextChanged {validatecard(editTextCardNumber.text.toString())}
    }
}