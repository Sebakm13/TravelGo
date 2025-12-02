package com.travelgo.app.data.Repository

import com.travelgo.app.data.remote.weather.WeatherResponse
import com.travelgo.app.data.remote.weather.WeatherService

open class WeatherRepository(
    private val api: WeatherService = WeatherService.instance
) {

    open suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
        return api.getWeather(city, apiKey)
    }
}
