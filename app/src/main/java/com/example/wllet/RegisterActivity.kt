package com.example.wllet

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
    }

    fun register(view: View) {

        var editEmailText = findViewById<EditText>(R.id.editTextEmail)
        var editNameText = findViewById<EditText>(R.id.editTextName)
        var editPasswordText = findViewById<EditText>(R.id.editTextPassword)
        var editConfirmPasswordText = findViewById<EditText>(R.id.editTextConfirmPassword)
        var checkTermsAndConditionsBox = findViewById<CheckBox>(R.id.checkBoxTermsAndConditions)
        var checkPrivacyPolicyBox = findViewById<CheckBox>(R.id.checkBoxPrivacyPolicy)
        var checkNewsLetterBox = findViewById<CheckBox>(R.id.checkBoxNewsletter)

        var email = editEmailText.text.toString()
        var name = editNameText.text.toString()
        var password = editPasswordText.text.toString()
        var confrimpassword = editConfirmPasswordText.text.toString()


        if (email.isEmpty() || name.isEmpty() || password.isEmpty()|| confrimpassword.isEmpty()) {
            Toast.makeText(baseContext, "All text fields must be filled", Toast.LENGTH_SHORT).show()
        }
        if (password != confrimpassword) {
            Toast.makeText(baseContext, "Passwords dose not match", Toast.LENGTH_SHORT).show()
        }
        if(checkTermsAndConditionsBox.isChecked) {
            Log.d("RegisterActivity", "Checked")
        }
        if(checkPrivacyPolicyBox.isChecked) {
            Log.d("RegisterActivity", "Checked")
        }
        if(checkNewsLetterBox.isChecked) {
            Log.d("RegisterActivity", "Cheked")
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                   if (!it.isSuccessful) return@addOnCompleteListener
                    Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(baseContext, "Registration failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}