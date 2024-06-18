package com.example.submissionstoryapp.response.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.capstonefix.response.Edit.EditRequest
import com.example.capstonefix.response.Edit.EditResponse
import com.example.capstonefix.response.Login.LoginResponse
import com.example.capstonefix.response.Login.loginRequest
import com.example.capstonefix.response.Register.RegisterRequest
import com.example.capstonefix.response.Register.RegisterResponse
import com.example.capstonefix.response.View.View
import com.example.capstonefix.response.add_point.AddPointRequest
import com.example.capstonefix.response.add_point.AddPointResponse
import com.example.submissionstoryapp.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AppRepository(private val apiService: ApiService) {





    fun editProfile(username: String, email: String, password: String): LiveData<Result<EditResponse>> = liveData {
        emit(Result.Loading)
        try {
            val registerRequest = EditRequest(username, email, password)
            val response = apiService.
            editProfile(registerRequest)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("SignUpViewModel", "postSignUp: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postSignUp(username: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val registerRequest = RegisterRequest(username, email, password)
            val response = apiService.postSignUp(registerRequest)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("SignUpViewModel", "postSignUp: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }


    fun postLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val LoginRequest = loginRequest(email, password)
            val response = apiService.postLogin(LoginRequest)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("LoginViewModel", "postLogin: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postAddPoint(pointsToAdd:Int): LiveData<Result<AddPointResponse>> = liveData {
        emit(Result.Loading)
        try {
            val addPointRequest = AddPointRequest(pointsToAdd)
            val response = apiService.addPoint(addPointRequest)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}