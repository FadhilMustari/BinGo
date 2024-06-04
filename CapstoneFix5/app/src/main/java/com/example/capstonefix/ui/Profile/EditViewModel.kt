package com.example.capstonefix.ui.Profile

import androidx.lifecycle.ViewModel
import com.example.submissionstoryapp.response.repository.AppRepository

class EditViewModel(private val storyRepository: AppRepository) : ViewModel() {
    fun edit(username: String,email: String, password: String) = storyRepository.editProfile(username,email, password)
}