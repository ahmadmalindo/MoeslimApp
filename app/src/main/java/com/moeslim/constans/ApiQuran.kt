package com.moeslim.constans

import com.google.gson.GsonBuilder
import com.moeslim.utils.getApiQuran
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiQuran {

    fun getDataApiQuran(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-alquranid.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getServiceDataAllQuran(): getApiQuran{
        return getDataApiQuran().create(getApiQuran::class.java)
    }
}