package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.travelgo.app.ui.PaqueteViewModel

@Composable
fun PaqueteEditScreen(editId: Long?, viewModel: PaqueteViewModel, onDone: () -> Unit) {
    val editItem = editId?.let { viewModel.getById(it) }

    var nombre by remember { mutableStateOf(editItem?.nombre ?: "") }
    var destino by remember { mutableStateOf(editItem?.destino ?: "") }
    var precio by remember { mutableStateOf(editItem?.precio?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(editItem?.descripcion ?: "") }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") })
        OutlinedTextField(destino, { destino = it }, label = { Text("Destino") })
        OutlinedTextField(precio, { precio = it }, label = { Text("Precio") })
        OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") })

        Button(
            onClick = {
                if (editId == null)
                    viewModel.add(nombre, destino, precio.toDouble(), descripcion)
                else
                    viewModel.update(editId, nombre, destino, precio.toDouble(), descripcion)

                onDone()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) { Text(if (editId == null) "Guardar" else "Actualizar") }
    }
}