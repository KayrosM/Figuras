package com.example.figuras.Model.Remote

import com.example.figuras.Model.Remote.FromInternet.DetailsFigure
import com.example.figuras.Model.Remote.FromInternet.ListFigure

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface FigureApi {

    @GET("musicStore")
    suspend fun fetchFigureList(): Response<List<ListFigure>>


    // seleccionar uno

    @GET("musicStore/{id}")
    suspend fun fetchFigureDetail(@Path("id") id: Int): Response<DetailsFigure>
}