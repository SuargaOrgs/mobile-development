package com.dicoding.suargaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ScanPredictResponse(

	@field:SerializedName("data")
	val data: DataScan? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Nutrition(

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

data class DataScan(

	@field:SerializedName("nutrition")
	val nutrition: Nutrition? = null,

	@field:SerializedName("indexValue")
	val indexValue: Double? = null
)
