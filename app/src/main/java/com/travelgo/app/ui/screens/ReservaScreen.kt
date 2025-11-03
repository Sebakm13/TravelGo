package com.travelgo.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ReservaScreen(
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    var fecha by rememberSaveable { mutableStateOf("") }
    var personas by rememberSaveable { mutableStateOf("") }
    var paquete by rememberSaveable { mutableStateOf("") }

    var fechaError by remember { mutableStateOf<String?>(null) }
    var personasError by remember { mutableStateOf<String?>(null) }
    var paqueteError by remember { mutableStateOf<String?>(null) }

    var loading by remember { mutableStateOf(false) }
    var success by remember { mutableStateOf(false) }

    fun validate(): Boolean {
        var ok = true
        if (paquete.isBlank()) {
            paqueteError = "Debes seleccionar un paquete"
            ok = false
        } else paqueteError = null

        if (fecha.isBlank()) {
            fechaError = "Debes indicar una fecha"
            ok = false
        } else fechaError = null

        val num = personas.toIntOrNull()
        if (num == null || num < 1) {
            personasError = "Debes ingresar un número válido de personas"
            ok = false
        } else personasError = null

        return ok
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Reserva",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Ingresa tus datos para pre-reservar",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = paquete,
            onValueChange = { paquete = it },
            label = { Text("Paquete turístico (ej: Atacama Eco)") },
            isError = paqueteError != null,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = paqueteError != null) {
            Text(
                text = paqueteError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (ej: 20/11/2025)") },
            isError = fechaError != null,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = fechaError != null) {
            Text(
                text = fechaError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = personas,
            onValueChange = { personas = it },
            label = { Text("Personas") },
            isError = personasError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = personasError != null) {
            Text(
                text = personasError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            enabled = !loading,
            onClick = {
                if (!validate()) return@Button
                scope.launch {
                    loading = true
                    success = false
                    delay(800) // Simula llamada al backend
                    loading = false
                    success = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.large
        ) {
            if (loading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Reservar ahora")
            }
        }

        AnimatedVisibility(visible = success) {
            Text(
                text = "Reserva registrada ✈️ Te contactaremos por correo.",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
