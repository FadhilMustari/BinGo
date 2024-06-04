package com.example.capstonefix.ui.Dashboard.dashboard


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import com.example.capstonefix.R
import com.example.capstonefix.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setOnItemSelectedListener { item ->
            val bundle = Bundle().apply {
                putString("username", username)
                putString("email", email)
                putString("password", password)
            }
            when (item) {
                R.id.homes -> {
                    navController.navigate(R.id.navigation_home, bundle)
                }
                R.id.profile -> {
                    navController.navigate(R.id.navigation_profile, bundle)
                }
                else -> {
                    false
                }
            }
            true
        }
    }
}
