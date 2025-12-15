package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.travelgo.app.data.Repository.ReservaRepository
import com.travelgo.app.data.db.DatabaseProvider
import com.travelgo.app.data.db.Reserva
import com.travelgo.app.ui.ReservaViewModel
import com.travelgo.app.ui.ReservaViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarReservaScreen(
    navController: NavController,
    reserva: Reserva
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val repo = ReservaRepository(DatabaseProvider.getDatabase(context).reservaDao())
    val vm: ReservaViewModel = viewModel(factory = ReservaViewModelFactory(repo))

    var fecha by remember { mutableStateOf(reserva.fecha) }
    var personas by remember { mutableStateOf(reserva.personas.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar reserva") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(reserva.nombrePaquete, style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = personas,
                onValueChange = { personas = it },
                label = { Text("Personas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    vm.update(
                        reserva.copy(
                            fecha = fecha,
                            personas = personas.toIntOrNull() ?: reserva.personas
                        )
                    )
                    navController.popBackStack()
                }
            ) {
                Text("Guardar cambios")
            }
        }
    }
}
