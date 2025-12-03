package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    // Si estamos editando, cargar el paquete
    LaunchedEffect(editId) {
        if (editId != null) {
            viewModel.getById(editId) { result ->
                paquete = result
            }
        }
    }

    var nombre by remember { mutableStateOf(paquete?.nombre ?: "") }
    var precio by remember { mutableStateOf(paquete?.precio?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(paquete?.descripcion ?: "") }

    Scaffold(
        topBar = {
            TopBarWithBack(
                navController = navController,
                title = "Mi Perfil"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del paquete") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio (USD)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val precioInt = precio.toIntOrNull() ?: 0

                    if (editId == null) {
                        // Crear nuevo paquete
                        viewModel.insert(
                            PaqueteLocal(
                                nombre = nombre,
                                descripcion = descripcion,
                                precio = precioInt
                            )
                        )
                    } else {
                        // Actualizar paquete existente
                        viewModel.update(
                            PaqueteLocal(
                                id = editId,
                                nombre = nombre,
                                descripcion = descripcion,
                                precio = precioInt,
                                creadoAt = paquete?.creadoAt ?: System.currentTimeMillis()
                            )
                        )
                    }

                    onDone()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = if (editId == null) "Guardar" else "Actualizar")
            }
        }
    }
}
