package com.example.capstonefix.response.Register

import com.google.gson.annotations.SerializedName

data class Register(
    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)