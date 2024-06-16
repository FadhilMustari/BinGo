package com.example.capstonefix.ui.Dashboard.dashboard


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import com.example.capstonefix.R
import com.example.capstonefix.databinding.ActivityDashboardBinding
import com.example.capstonefix.repository.Preference
import com.example.capstonefix.ui.Dashboard.dashboard.Fragment.HomeFragment
import com.example.capstonefix.ui.Dashboard.dashboard.Fragment.ProfileFragment
import com.example.capstonefix.ui.Dashboard.dashboard.Fragment.ScanFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation



class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val (username, email) = Preference.getInfo(this)

        val bundle = Bundle().apply {
            putString("username", username)
            putString("email", email)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigasi = findViewById<CurvedBottomNavigation>(R.id.bottomNav)
        bottomNavigasi.add(CurvedBottomNavigation.Model(1,"home",R.drawable.baseline_home_24))
        bottomNavigasi.add(CurvedBottomNavigation.Model(2,"scan",R.drawable.scan))
        bottomNavigasi.add(CurvedBottomNavigation.Model(3,"profile",R.drawable.profile2))

        bottomNavigasi.setOnClickMenuListener {
            when(it.id){
                1 -> {
                    replaceFragment(HomeFragment(),bundle)
                }
                2 -> {
                    replaceFragment(ScanFragment(),bundle)

                }
                3 -> {
                    replaceFragment(ProfileFragment(),bundle)
                }
            }
        }
        replaceFragment(HomeFragment(),bundle)
        bottomNavigasi.show(1)
    }

    private fun replaceFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}
