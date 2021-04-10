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
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var register: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register = FirebaseAuth.getInstance()
    }

    fun register(view: View) {
        var editEmailText = findViewById<EditText>(R.id.editTextEmail)
        var editNameText = findViewById<EditText>(R.id.editTextName)
        var editPasswordText = findViewById<EditText>(R.id.editTextPassword)
        var editConfirmPasswordText = findViewById<EditText>(R.id.editTextConfirmPassword)

        var email = editEmailText.text.toString()
        var name = editNameText.text.toString()
        var password = editPasswordText.text.toString()
        var confrimpassword = editConfirmPasswordText.text.toString()


        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            Toast.makeText(baseContext, "All text fields are required, terms and conditions are required and privacy policy is required", Toast.LENGTH_SHORT).show()
        } else if (password != confrimpassword) {
            Toast.makeText(baseContext, "Passwords dose not match", Toast.LENGTH_SHORT).show()
        } else {
            register.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()

                            saveUserToFirebaseDatabase()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(baseContext, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    private fun saveUserToFirebaseDatabase() {

        var uid = FirebaseAuth.getInstance().uid ?: ""
        var ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        var user = User(uid, editTextName.text.toString(),
                             editTextEmail.text.toString(),
                             checkBoxTermsAndConditions.text.toString(),
                             checkBoxPrivacyPolicy.text.toString(),
                             checkBoxNewsletter.text.toString())

        ref.setValue(user)
                .addOnSuccessListener {
                    Log.d("RegisterActivity", "User saved to firebase database")
                }
    }

    class User(val uid: String, val name: String, val email: String, val termsAndConditions: String, val privacyPolicy: String, val newsLetter: String)

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBoxTermsAndConditions -> {
                    if (checked) {
                        checkBoxTermsAndConditions.text = "Terms and conditions 'Red'"
                    } else if(!checked) {
                        checkBoxTermsAndConditions.text = "Terms and conditions 'Not red'"
                        Toast.makeText(baseContext, "Terms and conditions must be red", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.checkBoxPrivacyPolicy -> {
                    if (checked) {
                        checkBoxPrivacyPolicy.text = "Privacy policy 'Red'"

                    } else if(!checked) {
                        checkBoxPrivacyPolicy.text = "Privacy policy 'Not Red'"
                        Toast.makeText(baseContext, "Privacy policy must be checked", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.checkBoxNewsletter -> {
                    if (checked) {
                        checkBoxNewsletter.text = "Newsletter 'Yes'"

                    } else if(!checked) {
                        checkBoxNewsletter.text = "Newsletter 'No'"
                    }
                }
            }
        }
    }
}