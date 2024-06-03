package com.dicoding.suargaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetAssessmentResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataItem(

	@field:SerializedName("idAssessment")
	val idAssessment: Int? = null,

	@field:SerializedName("faktor")
	val faktor: String? = null,

	@field:SerializedName("beratBadan")
	val beratBadan: Int? = null,

	@field:SerializedName("karbohidrat")
	val karbohidrat: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("aktivitasHarian")
	val aktivitasHarian: String? = null,

	@field:SerializedName("protein")
	val protein: Int? = null,

	@field:SerializedName("tinggiBadan")
	val tinggiBadan: Int? = null,

	@field:SerializedName("lemak")
	val lemak: Int? = null
)
