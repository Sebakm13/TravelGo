package com.travelgo.app.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.data.db.PaqueteLocal
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.components.TopBarWithBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaqueteEditScreen(
    navController: NavController,
    editId: Long?,
    viewModel: PaqueteViewModel,
    onDone: () -> Unit
) {
    var paquete by remember { mutableStateOf<PaqueteLocal?>(null) }

    // Campos
    var nombre by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Errores por campo
    var nombreError by remember { mutableStateOf<String?>(null) }
    var destinoError by remember { mutableStateOf<String?>(null) }
    var precioError by remember { mutableStateOf<String?>(null) }
    var descripcionError by remember { mutableStateOf<String?>(null) }

    val isPressed = false
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "button_scale"
    )

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale, scaleY = scale),
        onClick = { /* guardar */ }
    ) {
        Text("Guardar")
    }

    // Cargar paquete si es edición
    LaunchedEffect(editId) {
        if (editId != null) {
            viewModel.getById(editId) { result ->
                paquete = result
                result?.let {
                    nombre = it.nombre
                    destino = it.destino
                    precio = it.precio.toString()
                    descripcion = it.descripcion
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarWithBack(
                navController = navController,
                title = if (editId == null) "Nuevo Paquete" else "Editar Paquete"
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = null
                },
                label = { Text("Nombre del paquete") },
                isError = nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            nombreError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = destino,
                onValueChange = {
                    destino = it
                    destinoError = null
                },
                label = { Text("Destino") },
                isError = destinoError != null,
                modifier = Modifier.fillMaxWidth()
            )
            destinoError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = precio,
                onValueChange = {
                    precio = it
                    precioError = null
                },
                label = { Text("Precio (USD)") },
                isError = precioError != null,
                modifier = Modifier.fillMaxWidth()
            )
            precioError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = descripcion,
                onValueChange = {
                    descripcion = it
                    descripcionError = null
                },
                label = { Text("Descripción") },
                isError = descripcionError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4
            )
            descripcionError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = {
                    // VALIDACIONES
                    var isValid = true

                    if (nombre.length < 3) {
                        nombreError = "El nombre debe tener al menos 3 caracteres"
                        isValid = false
                    }

                    if (destino.isBlank()) {
                        destinoError = "El destino es obligatorio"
                        isValid = false
                    }

                    val precioDouble = precio.toDoubleOrNull()
                    if (precioDouble == null || precioDouble <= 0) {
                        precioError = "Ingrese un precio válido"
                        isValid = false
                    }

                    if (descripcion.isBlank()) {
                        descripcionError = "La descripción es obligatoria"
                        isValid = false
                    }

                    if (!isValid) return@Button

                    if (editId == null) {
                        viewModel.insert(
                            PaqueteLocal(
                                nombre = nombre,
                                destino = destino,
                                descripcion = descripcion,
                                precio = precioDouble!!
                            )
                        )
                    } else {
                        viewModel.update(
                            PaqueteLocal(
                                id = editId,
                                nombre = nombre,
                                destino = destino,
                                descripcion = descripcion,
                                precio = precioDouble!!,
                                creadoAt = paquete?.creadoAt ?: System.currentTimeMillis()
                            )
                        )
                    }

                    onDone()
                }
            ) {
                Text(if (editId == null) "Guardar" else "Actualizar")
            }
        }
    }
}