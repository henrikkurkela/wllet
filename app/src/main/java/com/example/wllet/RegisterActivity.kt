package com.example.wllet

import android.content.Intent
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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Constants
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.random.Random

class RegisterActivity : AppCompatActivity() {

    private lateinit var register: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register = FirebaseAuth.getInstance()

    }

    fun register(view: View) {
        val editEmailText = findViewById<EditText>(R.id.editTextEmail)
        val editNameText = findViewById<EditText>(R.id.editTextName)
        val editPasswordText = findViewById<EditText>(R.id.editTextPassword)
        val editConfirmPasswordText = findViewById<EditText>(R.id.editTextConfirmPassword)
        val checkTermsAndCon = findViewById<CheckBox>(R.id.checkBoxTermsAndConditions)
        val checkPrivacyPolicy = findViewById<CheckBox>(R.id.checkBoxPrivacyPolicy)

        val email = editEmailText.text.toString()
        val name = editNameText.text.toString()
        val password = editPasswordText.text.toString()
        val confrimpassword = editConfirmPasswordText.text.toString()

        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || !checkTermsAndCon.isChecked || !checkPrivacyPolicy.isChecked) {
            Toast.makeText(baseContext, "All text fields are required, terms and conditions and privacy policy must be checked", Toast.LENGTH_SHORT).show()
        } else if (password != confrimpassword) {
            Toast.makeText(baseContext, "Passwords dose not match", Toast.LENGTH_SHORT).show()
        } else {
            register.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()

                            saveUserToFirebaseDatabase(newsLetter == true)

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(baseContext, "Registration failed: ${task.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    private fun saveUserToFirebaseDatabase(newsLetter: Boolean) {

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, editTextName.text.toString(),
                             editTextEmail.text.toString(),
                             newsLetter.toString().toBoolean(), 0.toString(), 0.toString() ,0.toString())

        ref.setValue(user)
                .addOnSuccessListener {
                    Log.d("RegisterActivity", "User saved to firebase database $newsLetter")
                }
    }

    class User(val uid: String, val name: String, val email: String, val newsLetter: Boolean, val btcbal: String, val ethbal: String, val gmebal: String)

    var newsLetter: Boolean? = null

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBoxNewsletter -> {
                    if (checked) {
                        newsLetter = true
                        Log.d("RegisterActivity", "News letter true saved to database $newsLetter")
                    } else {
                       newsLetter = false
                        Log.d("RegisterActivity", "User false saved to database $newsLetter")
                    }
                }
            }
        }
    }

    fun openTermsAndConditionsActivity(view: View) {
        val intent = Intent(this, TermsAndConditionsActivity::class.java)
        view.context.startActivity(intent)
    }

    fun openPrivacyPolicyActivity(view: View) {
        val intent = Intent(this, PrivacyPolicyActivity::class.java)
        view.context.startActivity(intent)
    }
}