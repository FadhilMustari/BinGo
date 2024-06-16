package com.example.capstonefix

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.capstonefix.databinding.ActivitySplashBinding
import com.example.capstonefix.ui.Awal.MainActivity
import com.example.capstonefix.ui.Login.LoginActivity
import com.example.capstonefix.ui.SignUp.SignUpActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@SplashActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        playAnimation()
    }
    private fun playAnimation() {
        binding.logoGambar.visibility = View.VISIBLE
        binding.containerJudul.visibility = View.VISIBLE
        binding.containerBtn.visibility = View.VISIBLE

        val logoGambar = ObjectAnimator.ofFloat(binding.logoGambar, View.ALPHA, 1f).setDuration(1000)
        val containerJudul = ObjectAnimator.ofFloat(binding.containerJudul, View.ALPHA, 1f).setDuration(1000)
        val containerBtn = ObjectAnimator.ofFloat(binding.containerBtn, View.ALPHA, 1f).setDuration(1000)

        val together =
            AnimatorSet().apply {
                playTogether(logoGambar, containerJudul)
                start()
            }
        AnimatorSet().apply {
            playSequentially(together, containerBtn)
            start()
        }
    }
}