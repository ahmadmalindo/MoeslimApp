package com.moeslim.utils

import com.moeslim.model.quran.ResponseApiDataAllQuran
import com.moeslim.model.quran.ResponseApiDetailDataQuran
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface getApiQuran {
    @GET("surah")
    fun getApiQuran(): Call<ResponseApiDataAllQuran>

    @GET("surah/{id}")
    fun getDetailApiAyyahQuran(@Path("id") id: Int): Call<ResponseApiDetailDataQuran>
}