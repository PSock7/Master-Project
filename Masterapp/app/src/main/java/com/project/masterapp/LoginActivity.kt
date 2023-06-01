package com.project.masterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    fun Registerdirection(view: View) {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }

    fun HomeRedirection(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }


}