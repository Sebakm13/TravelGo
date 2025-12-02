package com.travelgo.app.viewmodel

import com.travelgo.app.data.Repository.WeatherRepository
import com.travelgo.app.data.remote.weather.WeatherDescription
import com.travelgo.app.data.remote.weather.WeatherMain
import com.travelgo.app.data.remote.weather.WeatherResponse
import com.travelgo.app.data.remote.weather.WeatherService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class WeatherViewModelTest {

    class FakeWeatherRepository : WeatherRepository(WeatherService.instance) {

        override suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
            return WeatherResponse(
                name = "Santiago",
                main = WeatherMain(temp = 25.0, humidity = 20),
                weather = listOf(WeatherDescription(description = "Clear sky", icon = "01d"))

            )
        }
    }


    @Test
    fun `getWeather returns mocked data`() = runTest {
        val repo = FakeWeatherRepository()
        val result = repo.getWeather("Santiago", "TEST")

        assertEquals("Santiago", result.name)
        assertEquals(25.0, result.main.temp, 0.1)
    }
}
