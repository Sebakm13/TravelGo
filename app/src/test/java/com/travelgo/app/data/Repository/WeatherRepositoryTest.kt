package com.travelgo.app.data.Repository

import com.travelgo.app.data.remote.weather.WeatherApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {

    private lateinit var server: MockWebServer
    private lateinit var api: WeatherApi

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun `api retorna datos del clima correctamente`() = runTest {
        val mockResponse = """
            {
              "name": "Santiago",
              "main": { "temp": 25.0, "humidity": 50 },
              "weather": [
                { "description": "clear sky", "icon": "01d" }
              ]
            }
        """.trimIndent()

        server.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val result = api.getWeatherByCity("Santiago", "fake-key")

        assertEquals("Santiago", result.name)
        assertEquals(25.0, result.main.temp, 0.001)
        assertEquals(50, result.main.humidity)
        assertEquals("clear sky", result.weather[0].description)
    }
}
