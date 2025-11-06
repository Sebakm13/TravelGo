package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.components.TopBarWithBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaqueteDetailScreen(
    navController: NavController, // ✅ agregado
    id: Long,
    viewModel: PaqueteViewModel,
    onEdit: () -> Unit
) {
    val paquete = viewModel.getById(id)

    Scaffold(
        topBar = { TopBarWithBack(navController, title = "Detalle del paquete") }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(16.dp)) {
            Text("Destino: ${paquete?.destino}")
            Text("Precio: ${paquete?.precio}")
            Text("Descripción: ${paquete?.descripcion}")

            Spacer(Modifier.height(16.dp))
            Button(onClick = onEdit) { Text("Editar") }
        }
    }
}
