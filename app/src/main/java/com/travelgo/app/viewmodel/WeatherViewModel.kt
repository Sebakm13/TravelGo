package com.travelgo.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.Repository.WeatherRepository
import com.travelgo.app.data.remote.weather.WeatherResponse
import com.travelgo.app.data.remote.weather.WeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository =
        `WeatherRepository`(WeatherService.instance)
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState

    private val apiKey = "7a79854e91728678ddbeefb6c7977969"

    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, apiKey)
                _weatherState.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _weatherState.value = null
            }
        }
    }
}
