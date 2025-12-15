package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.travelgo.app.data.Repository.ReservaRepository
import com.travelgo.app.data.db.DatabaseProvider
import com.travelgo.app.ui.ReservaViewModel
import com.travelgo.app.ui.ReservaViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisReservasScreen(navController: NavController) {

    val context = androidx.compose.ui.platform.LocalContext.current
    val repo = ReservaRepository(DatabaseProvider.getDatabase(context).reservaDao())

    val vm: ReservaViewModel = viewModel(
        factory = ReservaViewModelFactory(repo)
    )

    val reservas by vm.reservas.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Reservar experiencia") }) }
    ) { padding ->

        if (reservas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Aún no tienes reservas.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                items(reservas) { r ->
                    Card(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(r.nombrePaquete, style = MaterialTheme.typography.titleLarge)
                            Text("Fecha: ${r.fecha}")
                            Text("Personas: ${r.personas}")
                            Text("Precio: $${r.precio}")
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton(onClick = { vm.delete(r) }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
