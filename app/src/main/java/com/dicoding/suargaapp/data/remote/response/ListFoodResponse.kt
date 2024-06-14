package com.dicoding.suargaapp.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ListFoodResponse(

	@field:SerializedName("data")
	val data: List<Food>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Food(

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
