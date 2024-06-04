package com.example.submissionstoryapp.network

import com.example.capstonefix.response.Edit.EditRequest
import com.example.capstonefix.response.Edit.EditResponse
import com.example.capstonefix.response.Login.Login
import com.example.capstonefix.response.Login.LoginResponse
import com.example.capstonefix.response.Login.loginRequest
import com.example.capstonefix.response.Register.RegisterRequest
import com.example.capstonefix.response.Register.RegisterResponse
import com.example.capstonefix.response.View.ViewResponse
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun postSignUp(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse


    @POST("login")
    suspend fun postLogin(
        @Body loginRequest: loginRequest
    ): LoginResponse

    @GET("view")
    suspend fun getProfile() : ViewResponse



    @PUT("edit")
    suspend fun editProfile(
        @Body editRequest: EditRequest
    ): EditResponse
}