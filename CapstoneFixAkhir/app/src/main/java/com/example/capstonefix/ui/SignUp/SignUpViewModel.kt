package com.example.capstonefix.ui.SignUp

import androidx.lifecycle.ViewModel
import com.example.submissionstoryapp.response.repository.AppRepository


class SignUpViewModel(private val storyRepository: AppRepository) : ViewModel() {

    fun signUp(username: String, email: String, password: String) =
        storyRepository.postSignUp(username, email, password)
}
