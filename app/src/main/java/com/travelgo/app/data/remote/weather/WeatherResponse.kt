package com.travelgo.app.data.remote.weather

data class WeatherResponse(
    val name: String,
    val main: WeatherMain,
    val weather: List<WeatherDescription>
)

data class WeatherMain(
    val temp: Double,
    val humidity: Int
)

data class WeatherDescription(
    val description: String,
    val icon: String
)
