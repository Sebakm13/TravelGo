package com.travelgo.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.travelgo.app.ui.PaqueteViewModel

@Composable
fun PaqueteListScreen(viewModel: PaqueteViewModel, onAdd: () -> Unit, onOpen: (Long) -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Text("+") }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            viewModel.paquetes.forEach { paquete ->
                Text(
                    text = "${paquete.nombre} — ${paquete.destino}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpen(paquete.id) }
                        .padding(16.dp)
                )
                Divider()
            }
        }
    }
}