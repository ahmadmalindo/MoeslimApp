package com.moeslim.model.quran

import com.google.gson.annotations.SerializedName

data class ResponseApiDataAllQuran(

	@field:SerializedName("data")
	val data: List<ItemDetail> = arrayListOf(),

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("Donate")
	val donate: Donate1? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Donate1(

	@field:SerializedName("Gopay")
	val gopay: String? = null
)

data class ItemDetail(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("rukuk")
	val rukuk: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("ayat")
	val ayat: Int? = null,

	@field:SerializedName("urut")
	val urut: String? = null,

	@field:SerializedName("arti")
	val arti: String? = null,

	@field:SerializedName("asma")
	val asma: String? = null,

	@field:SerializedName("audio")
	val audio: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("nomor")
	val nomor: String? = null
)
