package com.travelgo.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.ui.components.TopBarWithBack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ReservaScreen(navController: NavController) {
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

    Scaffold(
        topBar = { TopBarWithBack(navController, title = "Reserva tu experiencia") }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Reserva tu experiencia ✈️",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Ingresa tus datos y asegura tu viaje inolvidable",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(Modifier.height(24.dp))

                    OutlinedTextField(
                        value = paquete,
                        onValueChange = { paquete = it },
                        label = { Text("Paquete turístico") },
                        leadingIcon = {
                            Icon(Icons.Default.TravelExplore, contentDescription = null)
                        },
                        isError = paqueteError != null,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
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
                        leadingIcon = {
                            Icon(Icons.Default.CalendarMonth, contentDescription = null)
                        },
                        isError = fechaError != null,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
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
                        leadingIcon = {
                            Icon(Icons.Default.People, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = personasError != null,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
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
                                delay(800)
                                loading = false
                                success = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Reservar ahora", style = MaterialTheme.typography.bodyLarge)
                        }
                    }

                    AnimatedVisibility(
                        visible = success,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut()
                    ) {
                        Text(
                            text = "Reserva registrada. Te contactaremos pronto.",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
