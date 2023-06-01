package com.project.masterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.masterapp.fragments.HomeFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, HomeFragment(this))
        transaction.addToBackStack(null)
        transaction.commit()
    }
}