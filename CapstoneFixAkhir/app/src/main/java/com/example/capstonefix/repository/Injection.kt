package com.example.submissionstoryapp.response.repository


import android.content.Context
import com.example.submissionstoryapp.network.ApiConfig


object Injection {
    fun provideRepository(context: Context): AppRepository {
        val apiService = ApiConfig.getApiService(context)
        return AppRepository(apiService)
    }
}