package com.travelgo.app.data.Repository

import com.travelgo.app.data.remote.weather.WeatherService
import com.travelgo.app.data.remote.weather.WeatherResponse

class WeatherRepository {

    suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
        return WeatherService.api.getWeatherByCity(city, apiKey)
    }
}
