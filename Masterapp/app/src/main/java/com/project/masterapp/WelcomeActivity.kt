package com.project.masterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }
    public void register(View view){
        startActivity(new Intent(WelcomeActivity.this,RegistrationActivity.class))
    }
}