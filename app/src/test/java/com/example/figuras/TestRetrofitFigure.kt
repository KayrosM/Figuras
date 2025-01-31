package com.example.figuras

import com.example.figuras.Model.RetrofitFigure
import com.example.figuras.Model.Remote.FigureApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestRetrofitFigure {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: FigureApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // URL base simulada
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(FigureApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testRetrofitBaseUrl() {
        val expectedBaseUrl = mockWebServer.url("/").toString()
        val actualBaseUrl = RetrofitFigure.retrofitInstance().javaClass.getDeclaredField("baseUrl").also {
            it.isAccessible = true
        }.get(RetrofitFigure.retrofitInstance()).toString()

        assertEquals(expectedBaseUrl, actualBaseUrl)
    }

    @Test
    fun testFetchFigureList() = runBlocking {
        // Simulamos una respuesta del servidor con una lista vacía
        val mockResponse = MockResponse()
            .setBody("[]") // JSON vacío
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val response = apiService.fetchFigureList()

        assertEquals(true, response.isSuccessful)
        assertEquals(200, response.code())
    }
}
