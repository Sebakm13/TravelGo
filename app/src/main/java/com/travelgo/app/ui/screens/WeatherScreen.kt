package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.travelgo.app.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    var city by remember { mutableStateOf("Santiago") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Text(
            text = "Clima (API externa)",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.loadWeather(city) }) {
            Text("Cargar clima")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when {
            state.isLoading -> {
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cargando clima...")
                }
            }

            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.data != null -> {
                val weather = state.data!!
                Text("Ciudad: ${weather.name}")
                Text("Temperatura: ${weather.main.temp} °C")
                Text("Clima: ${weather.weather.firstOrNull()?.description ?: ""}")
            }

            else -> {
                Text("Ingresa una ciudad y presiona \"Cargar clima\".")
            }
        }
    }
}
