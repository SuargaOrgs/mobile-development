package com.dicoding.suargaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class NutritionManualResponse(

	@field:SerializedName("dataMakanan")
	val dataMakanan: DataMakanan? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,
)

data class DataMakanan(

	@field:SerializedName("idUser")
	val idUser: Int? = null,

	@field:SerializedName("NamaAktivitas")
	val namaAktivitas: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("waktuMakan")
	val waktuMakan: String? = null,

	@field:SerializedName("porsi")
	val porsi: Int? = null,

	@field:SerializedName("idMakanan")
	val idMakanan: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
)
