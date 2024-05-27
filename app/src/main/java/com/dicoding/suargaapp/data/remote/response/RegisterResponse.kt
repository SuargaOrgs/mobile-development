package com.dicoding.suargaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: DataRegister? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataRegister(

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("namaLengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
