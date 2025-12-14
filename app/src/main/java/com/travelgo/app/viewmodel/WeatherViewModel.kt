package com.travelgo.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.Repository.WeatherRepository
import com.travelgo.app.data.remote.weather.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WeatherUiState(
    val isLoading: Boolean = false,
    val data: WeatherResponse? = null,
    val error: String? = null
)

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    private val apiKey = "7a79854e91728678ddbeefb6c7977969"

    fun loadWeather(city: String) {
        if (city.isBlank()) {
            _uiState.value = WeatherUiState(error = "Ingrese una ciudad")
            return
        }

        _uiState.value = WeatherUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, apiKey)
                _uiState.value = WeatherUiState(data = response)
            } catch (e: Exception) {
                _uiState.value = WeatherUiState(
                    error = "No se pudo obtener el clima"
                )
            }
        }
    }
}
