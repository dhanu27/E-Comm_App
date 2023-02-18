package com.myyour.e_comm_app.api

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetroFitHelper {
    private const val base_url = "https://run.mocky.io/"
    fun getRetroFitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}