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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.ui.components.TopBarWithBack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
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

    // Validación
    fun validate(): Boolean {
        var ok = true

        if (paquete.isBlank()) {
            paqueteError = "Debes seleccionar un paquete"
            ok = false
        } else paqueteError = null

        if (fecha.isBlank()) {
            fechaError = "Debes indicar una fecha válida"
            ok = false
        } else fechaError = null

        val num = personas.toIntOrNull()
        if (num == null || num < 1) {
            personasError = "Ingresa un número válido"
            ok = false
        } else personasError = null

        return ok
    }

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = "Reserva tu experiencia",
                onBack = { navController.popBackStack() }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(padding)
        ) {

            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {

                Column(
                    modifier = Modifier.padding(26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // ==== TÍTULO ====
                    Text(
                        text = "Reserva tu experiencia ✈️",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Completa tu información para asegurar tu viaje.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(Modifier.height(24.dp))


                    // ==== PAQUETE ====
                    OutlinedTextField(
                        value = paquete,
                        onValueChange = { paquete = it },
                        label = { Text("Paquete turístico") },
                        leadingIcon = { Icon(Icons.Default.TravelExplore, contentDescription = null) },
                        isError = paqueteError != null,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    AnimatedVisibility(paqueteError != null) {
                        Text(
                            paqueteError.orEmpty(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.height(18.dp))


                    // ==== FECHA ====
                    OutlinedTextField(
                        value = fecha,
                        onValueChange = { fecha = it },
                        label = { Text("Fecha (ej: 21/01/2026)") },
                        leadingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
                        isError = fechaError != null,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    AnimatedVisibility(fechaError != null) {
                        Text(
                            fechaError.orEmpty(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.height(18.dp))


                    // ==== PERSONAS ====
                    OutlinedTextField(
                        value = personas,
                        onValueChange = { personas = it },
                        label = { Text("Número de personas") },
                        leadingIcon = { Icon(Icons.Default.People, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = personasError != null,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    AnimatedVisibility(personasError != null) {
                        Text(
                            personasError.orEmpty(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.height(26.dp))


                    // ==== BOTÓN ====
                    Button(
                        onClick = {
                            if (!validate()) return@Button

                            scope.launch {
                                loading = true
                                success = false
                                delay(1200)
                                loading = false
                                success = true
                            }
                        },
                        enabled = !loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Reservar ahora")
                        }
                    }


                    // ==== MENSAJE DE ÉXITO ====
                    AnimatedVisibility(
                        visible = success,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut()
                    ) {
                        Text(
                            text = "¡Reserva registrada correctamente! 📩",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }
            }
        }
    }
}
