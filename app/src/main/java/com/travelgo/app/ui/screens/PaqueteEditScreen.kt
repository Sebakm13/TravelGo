package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.data.db.Paquete
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

    var paquete by remember { mutableStateOf<Paquete?>(null) }

    var nombre by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // 🔹 Cargar paquete si es edición
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
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = destino,
                onValueChange = { destino = it },
                label = { Text("Destino") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 4
            )

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val precioDouble = precio.toDoubleOrNull() ?: return@Button

                    if (editId == null) {
                        viewModel.insert(
                            Paquete(
                                nombre = nombre,
                                destino = destino,
                                descripcion = descripcion,
                                precio = precioDouble
                            )
                        )
                    } else {
                        viewModel.update(
                            Paquete(
                                id = editId,
                                nombre = nombre,
                                destino = destino,
                                descripcion = descripcion,
                                precio = precioDouble,
                                imagenUri = paquete?.imagenUri,
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
