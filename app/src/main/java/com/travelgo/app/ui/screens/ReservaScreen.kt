package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.travelgo.app.data.Repository.ReservaRepository
import com.travelgo.app.data.db.DatabaseProvider
import com.travelgo.app.data.db.Reserva
import com.travelgo.app.ui.ReservaViewModel
import com.travelgo.app.ui.ReservaViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaScreen(
    navController: NavController,
    paqueteId: Long,
    nombreArg: String,
    descArg: String,
    precioArg: Double
) {
    val scope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current

    val repo = ReservaRepository(DatabaseProvider.getDatabase(context).reservaDao())
    val vm: ReservaViewModel = viewModel(factory = ReservaViewModelFactory(repo))

    var fecha by rememberSaveable { mutableStateOf("") }
    var personas by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Reserva", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = nombreArg,
                onValueChange = {},
                enabled = false,
                label = { Text("Paquete turístico") },
                leadingIcon = { Icon(Icons.Default.TravelExplore, null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                leadingIcon = { Icon(Icons.Default.CalendarMonth, null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = personas,
                onValueChange = { personas = it },
                label = { Text("Número de personas") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(Icons.Default.People, null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                onClick = {
                    scope.launch {
                        vm.insert(
                            Reserva(
                                paqueteId = paqueteId,
                                nombrePaquete = nombreArg,
                                descripcion = descArg,
                                precio = precioArg,
                                fecha = fecha,
                                personas = personas.toInt()
                            )
                        )
                        navController.navigate("mis_reservas")
                    }
                }
            ) {
                Text("Reservar ahora")
            }
        }
    }
}
