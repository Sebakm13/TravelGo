package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.travelgo.app.data.Paquete
import com.travelgo.app.data.db.DatabaseProvider
import com.travelgo.app.data.PaqueteRepository
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.PaqueteViewModelFactory

@Composable
fun PaqueteEditScreen(
    paqueteId: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val repo = PaqueteRepository(db.paqueteLocal())

    val viewModel: PaqueteViewModel = viewModel(
        factory = PaqueteViewModelFactory(repo)
    )

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    LaunchedEffect(paqueteId) {
        if (paqueteId != 0) {
            val paquete = viewModel.obtenerPorId(paqueteId)
            paquete?.let {
                titulo = it.titulo
                descripcion = it.descripcion
                precio = it.precio.toString()
            }
        }
    }

    Scaffold(
        topBar = { Text("Editar Paquete", modifier = Modifier.padding(16.dp)) }
    ) { padding ->

        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") }
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") }
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") }
            )

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                val p = Paquete(
                    id = paqueteId,
                    titulo = titulo,
                    descripcion = descripcion,
                    precio = precio.toIntOrNull() ?: 0
                )
                viewModel.insertar(p)
                onBack()
            }) {
                Text("Guardar")
            }
        }
    }
}
