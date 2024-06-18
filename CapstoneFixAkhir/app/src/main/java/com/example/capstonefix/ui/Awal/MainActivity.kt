package com.example.capstonefix.ui.Awal

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.capstonefix.SplashActivity
import com.example.capstonefix.databinding.ActivityMainBinding
import com.example.capstonefix.repository.Preference
import com.example.capstonefix.ui.Dashboard.dashboard.DashboardActivity
import com.example.capstonefix.ui.Login.LoginActivity
import com.example.capstonefix.ui.SignUp.SignUpActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.containerPage.setOnClickListener{
            val intent = Intent(this@MainActivity, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }

        val token = Preference.getToken(this)
        if(token != null){
            val (username, email, point) = Preference.getInfo(this)
            val intent = Intent(this,DashboardActivity::class.java)
            intent.putExtra("username",username)
            intent.putExtra("email",email)
            intent.putExtra("point",point)
            startActivity(intent)
            finish()
        }
    }

}
