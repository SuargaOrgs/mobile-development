package com.dicoding.suargaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,
)

data class Data(

	@field:SerializedName("idUser")
	val idUser: Int? = null,

	@field:SerializedName("tanggalKehamilan")
	val tanggalKehamilan: String? = null,

	@field:SerializedName("tanggalLahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("namaLengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
