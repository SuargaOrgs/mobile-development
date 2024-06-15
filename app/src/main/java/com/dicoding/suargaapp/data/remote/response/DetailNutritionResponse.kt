package com.dicoding.suargaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailNutritionResponse(

	@field:SerializedName("data")
	val data: List<DataMakanan?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataMakanan(

	@field:SerializedName("idUser")
	val idUser: Int? = null,

	@field:SerializedName("karbohidrat")
	val karbohidrat: Double? = null,

	@field:SerializedName("waktuMakan")
	val waktuMakan: String? = null,

	@field:SerializedName("porsi")
	val porsi: Int? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("idMakanan")
	val idMakanan: Int? = null,

	@field:SerializedName("update_at")
	val updateAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("namaMakanan")
	val namaMakanan: String? = null,

	@field:SerializedName("lemak")
	val lemak: Double? = null
)
