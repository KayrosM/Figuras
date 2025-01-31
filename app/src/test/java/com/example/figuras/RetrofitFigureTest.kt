package com.example.figuras

import com.example.figuras.Model.RetrofitFigure
import com.example.figuras.Model.Remote.FigureApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFigureTest {

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
    fun `test API fetchFigureList returns success`() = runBlocking {
        // Simulamos una respuesta JSON vacía con código 200
        val mockResponse = MockResponse()
            .setBody("[]")
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        // Llamada a la API
        val response = apiService.fetchFigureList()

        // Verificar que la respuesta fue exitosa
        assertTrue(response.isSuccessful)
    }
}
