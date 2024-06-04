package com.example.capstonefix.ui.Login

import androidx.lifecycle.ViewModel
import com.example.submissionstoryapp.response.repository.AppRepository

class LoginViewModel(private val storyRepository: AppRepository) : ViewModel() {
    fun login(email: String, password: String) = storyRepository.postLogin(email, password)
}