package com.example.capstonefix.repository

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstonefix.ui.Login.LoginViewModel
import com.example.capstonefix.ui.Profile.EditViewModel
import com.example.capstonefix.ui.SignUp.SignUpViewModel
import com.example.submissionstoryapp.response.repository.Injection


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(EditViewModel::class.java) -> {
                EditViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideRepository(context)) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}