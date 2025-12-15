package com.travelgo.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.travelgo.app.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        Button(
            onClick = { viewModel.loadWeather("Santiago") }
        ) {
            Text("Cargar clima de Santiago")
        }

        AnimatedVisibility(
            visible = state.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CircularProgressIndicator()
        }

        // 👇 AQUÍ VAN LOS RESULTADOS
        state.data?.let { weather ->
            Text(weather.name)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.data != null -> {
                val weather = state.data!!

                Text("Ciudad: ${weather.name}")
                Text("Temperatura: ${weather.main.temp} °C")
                Text("Clima: ${weather.weather.firstOrNull()?.description}")
            }

            else -> {
                Text("Sin datos aún…")
            }
        }
    }
}
