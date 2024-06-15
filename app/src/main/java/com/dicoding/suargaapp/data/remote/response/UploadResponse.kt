package com.dicoding.suargaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("foodData")
	val data: FoodData? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class FoodData(

	@field:SerializedName("dataNutrition")
	val dataNutrition: DataNutrition? = null,

	@field:SerializedName("namaAktivitas")
	val namaAktivitas: String? = null,

	@field:SerializedName("waktuMakan")
	val waktuMakan: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("porsi")
	val porsi: String? = null
)

data class DataNutrition(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("karbohidrat")
	val karbohidrat: Double? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("vitamin")
	val vitamin: String? = null,

	@field:SerializedName("namaMakanan")
	val namaMakanan: String? = null,

	@field:SerializedName("lemak")
	val lemak: Double? = null
)
