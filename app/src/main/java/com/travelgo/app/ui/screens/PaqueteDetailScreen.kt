package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.travelgo.app.data.Repository.PaqueteRepository
import com.travelgo.app.data.db.DatabaseProvider
import com.travelgo.app.data.db.Paquete
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.PaqueteViewModelFactory
import com.travelgo.app.ui.components.TopBarWithBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaqueteDetailScreen(
    navController: NavController,
    id: Long,
    onReservar: () -> Unit,
    onEdit: () -> Unit
) {
    val context = LocalContext.current
    val repo = PaqueteRepository(DatabaseProvider.getDatabase(context).paqueteDao())

    val viewModel: PaqueteViewModel = viewModel(
        factory = PaqueteViewModelFactory(repo)
    )

    var paquete by remember { mutableStateOf<Paquete?>(null) }

    LaunchedEffect(id) {
        viewModel.getById(id) { paquete = it }
    }

    Scaffold(
        topBar = { TopBarWithBack(navController, "Detalle del paquete") }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Nombre: ${paquete?.nombre}")
            Text("Precio: ${paquete?.precio}")
            Text("Descripción: ${paquete?.descripcion}")

            Spacer(Modifier.height(16.dp))

            Button(onClick = onReservar, enabled = paquete != null) {
                Text("Reservar este viaje")
            }
        }
    }
}
