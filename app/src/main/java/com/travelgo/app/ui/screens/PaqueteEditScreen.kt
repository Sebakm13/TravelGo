package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.components.TopBarWithBack

@Composable
fun PaqueteEditScreen(
    navController: NavController,
    viewModel: PaqueteViewModel,
    id: Long?
) {
    val paquete = id?.let { viewModel.getById(it) }

    var nombre by remember { mutableStateOf(paquete?.nombre ?: "") }
    var destino by remember { mutableStateOf(paquete?.destino ?: "") }
    var precioText by remember { mutableStateOf(paquete?.precio?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(paquete?.descripcion ?: "") }

    val isEdit = id != null

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = if (isEdit) "Editar paquete" else "Nuevo paquete",
                navController = navController
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                value = precioText,
                onValueChange = { precioText = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val precio = precioText.toDoubleOrNull() ?: 0.0
                    if (isEdit && id != null) {
                        viewModel.update(
                            id = id,
                            nombre = nombre,
                            destino = destino,
                            precio = precio,
                            descripcion = descripcion
                        )
                    } else {
                        viewModel.add(
                            nombre = nombre,
                            destino = destino,
                            precio = precio,
                            descripcion = descripcion
                        )
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEdit) "Guardar cambios" else "Crear paquete")
            }
        }
    }
}
