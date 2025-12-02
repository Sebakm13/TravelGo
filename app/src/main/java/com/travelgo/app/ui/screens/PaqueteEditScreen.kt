package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
    val editItem = editId?.let { viewModel.getById(it) }

    var nombre by remember { mutableStateOf(editItem?.nombre ?: "") }
    var destino by remember { mutableStateOf(editItem?.destino ?: "") }
    var precio by remember { mutableStateOf(editItem?.precio?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(editItem?.descripcion ?: "") }

    Scaffold(
        topBar = { TopBarWithBack(navController, title = if (editId == null) "Nuevo Paquete" else "Editar Paquete") },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (editId == null) "Crea un nuevo paquete" else "Actualiza los detalles",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del paquete") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = destino,
                onValueChange = { destino = it },
                label = { Text("Destino") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio (USD)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (editId == null)
                        viewModel.add(nombre, destino, precio.toDoubleOrNull() ?: 0.0, descripcion)
                    else
                        viewModel.update(editId, nombre, destino, precio.toDoubleOrNull() ?: 0.0, descripcion)

                    onDone()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = if (editId == null) "Guardar" else "Actualizar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
