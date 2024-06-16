package com.example.capstonefix.response.Register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("mesaage")
	val mesaage: String? = null,

	@field:SerializedName("data")
	val data: Register? = null
)


