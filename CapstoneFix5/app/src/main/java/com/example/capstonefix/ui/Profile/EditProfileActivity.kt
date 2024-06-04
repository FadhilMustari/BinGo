package com.example.capstonefix.ui.Profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.capstonefix.R
import com.example.capstonefix.databinding.ActivityEditProfileBinding
import com.example.capstonefix.repository.ViewModelFactory
import com.example.capstonefix.response.Edit.EditResponse
import com.example.capstonefix.response.Register.RegisterResponse
import com.example.capstonefix.ui.Dashboard.dashboard.DashboardActivity
import com.example.capstonefix.ui.Dashboard.dashboard.Fragment.ProfileFragment
import com.example.capstonefix.ui.Login.LoginActivity
import com.example.capstonefix.ui.SignUp.SignUpViewModel
import com.example.submissionstoryapp.response.repository.Result

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditProfileBinding
    private val editViewModel: EditViewModel by viewModels{
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        binding.inputUsername.setText(username)
        binding.inputEmail.setText(email)
        binding.inputPassword.setText(password)

        binding.btnSimpan.setOnClickListener{
            val name = binding.inputUsername.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            editViewModel.edit(name, email, password).observe(this) {
                if (it != null) {
                    when(it) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            processEdit(it.data)
                        }
                        is Result.Error -> {
                            Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    private fun processEdit(data: EditResponse) {
        if (data.message == "fail") {
            Toast.makeText(this, "Gagal Edit", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Edit berhasil", Toast.LENGTH_LONG).show()
            val name = binding.inputUsername.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            val intent = Intent(this@EditProfileActivity, DashboardActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra("username",name)
            intent.putExtra("email",email)
            intent.putExtra("password",password)
            startActivity(intent)
            finish()
        }
    }
}