package com.example.capstonefix.response.add_point

import com.google.gson.annotations.SerializedName

data class AddPointResponse (
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)