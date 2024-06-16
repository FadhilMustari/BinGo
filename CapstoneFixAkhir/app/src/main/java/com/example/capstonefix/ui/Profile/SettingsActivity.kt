package com.example.capstonefix.ui.Profile

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonefix.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val editProfile = findViewById<TextView>(R.id.editProfile)
        editProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigationButtons)
//        bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.navigation_home -> {
//                    val intent = Intent(this, HomeActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                R.id.navigation_profile -> {
//                    val intent = Intent(this, SettingsActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                else -> false
//            }
//        }
    }
}