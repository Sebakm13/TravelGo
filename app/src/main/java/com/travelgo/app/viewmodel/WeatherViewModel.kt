package com.travelgo.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelgo.app.data.remote.weather.WeatherResponse
import com.travelgo.app.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WeatherUiState(
    val isLoading: Boolean = false,
    val data: WeatherResponse? = null,
    val errorMessage: String? = null
)

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherUiState())
    val state: StateFlow<WeatherUiState> = _state


    private val apiKey = "3d852714f8f87fa386c3e6ceb393ee2a"

    fun loadWeather(city: String) {
        _state.value = _state.value.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, apiKey)
                _state.value = WeatherUiState(
                    isLoading = false,
                    data = response,
                    errorMessage = null
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = WeatherUiState(
                    isLoading = false,
                    data = null,
                    errorMessage = "Error al obtener el clima. Revisa tu conexión."
                )
            }
        }
    }
}

