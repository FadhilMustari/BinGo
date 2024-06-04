package com.example.capstonefix.ui.Login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.capstonefix.databinding.ActivityLoginBinding
import com.example.capstonefix.repository.Preference
import com.example.capstonefix.repository.ViewModelFactory
import com.example.capstonefix.response.Login.LoginResponse
import com.example.capstonefix.ui.Dashboard.dashboard.DashboardActivity
import com.example.capstonefix.ui.Sambutan.SambutanLogin
import com.example.capstonefix.ui.SignUp.SignUpActivity
import com.example.submissionstoryapp.response.repository.Result

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(this)
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()

        binding.btnLogin.setOnClickListener{
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            loginViewModel.login(email, password).observe(this) { result ->
                if (result != null) {
                    when(result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            processLogin(result.data, password)
                            showLoading(false)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        binding.btnDaftar.setOnClickListener{
            val intent = Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }

    }
    private fun processLogin(data: LoginResponse, password: String) {
        if (data.status == "fail") {
            Toast.makeText(this, data.mesaage, Toast.LENGTH_LONG).show()
        } else {
            data.token?.let { Preference.saveToken(it, this) }
            val intent = Intent(this@LoginActivity, SambutanLogin::class.java)
            intent.putExtra("username", data.data?.username)
            intent.putExtra("email", data.data?.email)
            intent.putExtra("password", password)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading(state: Boolean) {
        binding.logoGambar.isInvisible = state
        binding.pbLogin.isVisible = state
        binding.inputEmail.isInvisible = state
        binding.inputPassword.isInvisible = state
        binding.btnLogin.isInvisible = state
        binding.containerDaftar.isInvisible = state
    }

    private fun playAnimation() {
        binding.logoGambar.visibility = View.VISIBLE
        binding.Judul.visibility = View.VISIBLE
        binding.keterangan.visibility = View.VISIBLE
        binding.containerInput.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.VISIBLE
        binding.containerDaftar.visibility = View.VISIBLE


        val logoGambar = ObjectAnimator.ofFloat(binding.logoGambar, View.ALPHA, 1f).setDuration(1000)
        val Judul = ObjectAnimator.ofFloat(binding.Judul, View.ALPHA, 1f).setDuration(1000)
        val keterangan = ObjectAnimator.ofFloat(binding.keterangan, View.ALPHA, 1f).setDuration(1000)
        val containerInput = ObjectAnimator.ofFloat(binding.containerInput, View.ALPHA, 1f).setDuration(1000)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(1000)
        val containerSignup = ObjectAnimator.ofFloat(binding.containerDaftar, View.ALPHA, 1f).setDuration(1000)


        val together =
            AnimatorSet().apply {
                playTogether(Judul, keterangan, containerInput)
            }
        AnimatorSet().apply {
            playSequentially(logoGambar, together, btnLogin, containerSignup)
            start()
        }
    }

}