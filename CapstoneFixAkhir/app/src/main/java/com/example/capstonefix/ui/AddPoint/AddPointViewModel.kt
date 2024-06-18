package com.example.capstonefix.ui.AddPoint

import androidx.lifecycle.ViewModel
import com.example.submissionstoryapp.response.repository.AppRepository

class AddPointViewModel(private val storyRepository: AppRepository) : ViewModel() {
    fun addPoint(point : Int) = storyRepository.postAddPoint(point)
}