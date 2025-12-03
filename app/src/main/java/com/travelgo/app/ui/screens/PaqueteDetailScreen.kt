package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.data.db.PaqueteLocal
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.components.TopBarWithBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaqueteDetailScreen(
    navController: NavController,
    id: Long,
    viewModel: PaqueteViewModel,
    onEdit: () -> Unit
) {
    var paquete by remember { mutableStateOf<PaqueteLocal?>(null) }

    // Cargar el paquete desde el ViewModel
    LaunchedEffect(id) {
        viewModel.getById(id) { result ->
            paquete = result
        }
    }

    Scaffold(
        topBar = { TopBarWithBack(navController, title = "Detalle del paquete") }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Nombre: ${paquete?.nombre}")
            Text("Precio: ${paquete?.precio}")
            Text("Descripción: ${paquete?.descripcion}")

            Spacer(Modifier.height(16.dp))

            Button(
                enabled = paquete != null,
                onClick = onEdit
            ) {
                Text("Editar")
            }
        }
    }
}
