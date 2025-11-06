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
    navController: NavController, // ✅ agregado
    editId: Long?,
    viewModel: PaqueteViewModel,
    onDone: () -> Unit
) {
    val editItem = editId?.let { viewModel.getById(it) }

    var nombre by remember { mutableStateOf(editItem?.nombre ?: "") }
    var destino by remember { mutableStateOf(editItem?.destino ?: "") }
    var precio by remember { mutableStateOf(editItem?.precio?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(editItem?.descripcion ?: "") }

    Scaffold(
        topBar = { TopBarWithBack(navController, title = if (editId == null) "Nuevo paquete" else "Editar paquete") }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(16.dp)) {
            OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") })
            OutlinedTextField(destino, { destino = it }, label = { Text("Destino") })
            OutlinedTextField(precio, { precio = it }, label = { Text("Precio") })
            OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") })

            Button(
                onClick = {
                    if (editId == null)
                        viewModel.add(nombre, destino, precio.toDoubleOrNull() ?: 0.0, descripcion)
                    else
                        viewModel.update(editId, nombre, destino, precio.toDoubleOrNull() ?: 0.0, descripcion)

                    onDone()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(if (editId == null) "Guardar" else "Actualizar")
            }
        }
    }
}
