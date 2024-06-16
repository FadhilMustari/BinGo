package com.example.capstonefix.response.Edit

import com.google.gson.annotations.SerializedName

data class EditResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
