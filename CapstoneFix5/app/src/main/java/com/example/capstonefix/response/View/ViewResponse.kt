package com.example.capstonefix.response.View

import com.google.gson.annotations.SerializedName

data class ViewResponse(

	@field:SerializedName("data")
	val data: List<View?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)


