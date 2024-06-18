package com.example.capstonefix.response.Login

import com.google.gson.annotations.SerializedName

data class Login(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("point")
    val point: Int? = null
)