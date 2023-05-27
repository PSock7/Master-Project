package com.project.masterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.content.Intent
import android.view.View


class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }
    fun register(view: View) {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }
    fun login(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

}