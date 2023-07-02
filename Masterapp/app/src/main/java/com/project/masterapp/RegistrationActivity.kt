package com.project.masterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        this.auth = Firebase.auth
        val loginText: TextView = findViewById(R.id.textView_login_now)
        loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        // lets get email and name and password from the user
        val registerButton: Button = findViewById(R.id.button_register)
        registerButton.setOnClickListener{
            perfomSignUp()
        }

    }

    private fun perfomSignUp() {
        val email = findViewById<EditText>(R.id.editText_email_register)
        val name = findViewById<EditText>(R.id.editText_name_register)
        val password = findViewById<EditText>(R.id.editText_password_register)

        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val inputEmail = email.text.toString()
        val inputName = name.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // L'utilisateur a été créé avec succès
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(inputName)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                // Le profil de l'utilisateur a été mis à jour avec succès
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(
                                    baseContext,
                                    "Account created successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // Échec de la mise à jour du profil de l'utilisateur
                                Toast.makeText(
                                    baseContext,
                                    "Failed to update user profile.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    // Échec de la création de l'utilisateur
                    Toast.makeText(
                        baseContext,
                        "Failed to create account.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occurred: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
}
