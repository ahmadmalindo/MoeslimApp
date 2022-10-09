package com.moeslim.model.quran

import com.google.gson.annotations.SerializedName

data class ResponseApiDetailDataQuran(

	@field:SerializedName("data")
	val data: ArrayList<DataItem> = arrayListOf(),

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("Donate")
	val donate: Donate? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("ar")
	val ar: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("nomor")
	val nomor: String? = null,

	@field:SerializedName("tr")
	val tr: String? = null
)

data class Donate(

	@field:SerializedName("Gopay")
	val gopay: String? = null
)
