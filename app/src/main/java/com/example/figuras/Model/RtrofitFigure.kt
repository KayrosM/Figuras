package com.example.figuras.Model

import com.example.figuras.Model.Remote.FigureApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFigure {
    private const val BASE_URL = "https://caso-music-mariocanedo.vercel.app/"

    fun retrofitInstance(): FigureApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FigureApi::class.java)
    }
}
