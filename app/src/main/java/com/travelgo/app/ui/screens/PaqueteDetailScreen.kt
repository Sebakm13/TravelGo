package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.components.TopBarWithBack

@Composable
fun PaqueteDetailScreen(
    navController: NavController,
    id: Long,
    viewModel: PaqueteViewModel,
    onEdit: () -> Unit
) {
    val paquete = viewModel.getById(id)

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = "Detalle paquete",
                navController = navController
            )
        }
    ) { padding ->
        if (paquete == null) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Paquete no encontrado")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = paquete.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Destino: ${paquete.destino}")
                Text(text = "Precio: $${paquete.precio}")
                Text(text = "Descripción:")
                Text(text = paquete.descripcion)

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(onClick = onEdit) {
                        Text("Editar")
                    }
                    OutlinedButton(onClick = {
                        viewModel.delete(id)
                        navController.popBackStack()
                    }) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}
