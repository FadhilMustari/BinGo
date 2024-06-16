package com.example.capstonefix.ui.Sambutan

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.capstonefix.databinding.ActivitySambutanSignUpBinding
import com.example.capstonefix.ui.Dashboard.dashboard.DashboardActivity

class SambutanSignUp : AppCompatActivity() {
    private lateinit var binding : ActivitySambutanSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySambutanSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sambutanSignUp.setOnClickListener{
            val intent = Intent(this@SambutanSignUp, DashboardActivity::class.java )
            startActivity(intent)
            finish()
        }
        playAnimation()
    }
    private fun playAnimation() {
        binding.logoGambar.visibility = View.VISIBLE
        binding.Judul.visibility = View.VISIBLE
        binding.keterangan.visibility = View.VISIBLE
        binding.infoTap.visibility = View.VISIBLE



        val logoGambar = ObjectAnimator.ofFloat(binding.logoGambar, View.ALPHA, 1f).setDuration(1000)
        val Judul = ObjectAnimator.ofFloat(binding.Judul, View.ALPHA, 1f).setDuration(1000)
        val keterangan = ObjectAnimator.ofFloat(binding.keterangan, View.ALPHA, 1f).setDuration(1000)
        val infoTap = ObjectAnimator.ofFloat(binding.infoTap, View.ALPHA, 1f).setDuration(5000)


        val together =
            AnimatorSet().apply {
                playTogether(Judul, keterangan)
            }
        AnimatorSet().apply {
            playSequentially(together, logoGambar, infoTap)
            start()
        }
    }
}