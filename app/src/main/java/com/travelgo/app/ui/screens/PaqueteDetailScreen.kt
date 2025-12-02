package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.travelgo.app.ui.PaqueteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaqueteDetailScreen(
    paqueteId: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    val viewModel: PaqueteViewModel = viewModel()
    var paquete by remember { mutableStateOf<com.travelgo.app.data.Paquete?>(null) }

    LaunchedEffect(paqueteId) {
        paquete = viewModel.obtenerPorId(paqueteId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Paquete") }
            )
        }
    ) { paddingValues ->

        paquete?.let { p ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                Text(
                    text = p.titulo,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = p.descripcion)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Precio: ${p.precio}")
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Button(onClick = onEdit) {
                        Text("Editar")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedButton(onClick = onBack) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}
