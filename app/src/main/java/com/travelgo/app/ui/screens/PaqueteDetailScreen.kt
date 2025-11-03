package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.travelgo.app.ui.PaqueteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaqueteDetailScreen(id: Long, viewModel: PaqueteViewModel, onEdit: () -> Unit, onBack: () -> Unit) {
    val paquete = viewModel.getById(id)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(paquete?.nombre ?: "Paquete") },
                navigationIcon = { IconButton(onClick = onBack) { Text("<") } },
                actions = { IconButton(onClick = onEdit) { Text("✎") } }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text("Destino: ${paquete?.destino}")
            Text("Precio: ${paquete?.precio}")
            Text("Descripción: ${paquete?.descripcion}")
        }
    }
}