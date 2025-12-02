package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.travelgo.app.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {

    val state = viewModel.weatherState.collectAsState(initial = null).value

    Column(modifier = Modifier.padding(16.dp)) {

        Button(onClick = { viewModel.loadWeather("Santiago") }) {
            Text("Cargar clima de Santiago")
        }

        Spacer(modifier = Modifier.height(16.dp))

        state?.let { weather ->
            Text("Ciudad: ${weather.name}")
            Text("Temperatura: ${weather.main.temp} °C")
            Text("Clima: ${weather.weather.first().description}")
        } ?: Text("Sin datos aún…")
    }
}
