package com.example.capstonefix.response.View

import com.google.gson.annotations.SerializedName

data class View(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)