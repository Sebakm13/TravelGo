package com.travelgo.app.data.remote.weather


data class WeatherResponse(
    val name: String,
    val main: MainInfo,
    val weather: List<WeatherInfo>
)

data class MainInfo(
    val temp: Double,
    val humidity: Int
)

data class WeatherInfo(
    val description: String,
    val icon: String
)
