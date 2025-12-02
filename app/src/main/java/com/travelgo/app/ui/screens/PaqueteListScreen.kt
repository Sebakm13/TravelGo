package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.travelgo.app.data.Paquete
import com.travelgo.app.data.PaqueteRepository
import com.travelgo.app.data.db.DatabaseProvider
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.PaqueteViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaqueteListScreen(
    onPaqueteClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    // --- ACCESO A ROOM ---
    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val repo = PaqueteRepository(db.paqueteLocal())

    val viewModel: PaqueteViewModel = viewModel(
        factory = PaqueteViewModelFactory(repo)
    )

    val paquetes by viewModel.paquetes.collectAsState()

    // --- CARGAR DATOS DE PRUEBA LA PRIMERA VEZ ---
    LaunchedEffect(Unit) {
        if (paquetes.isEmpty()) {
            viewModel.insertar(
                Paquete(
                    titulo = "Tour Eco Aventura",
                    descripcion = "Explora montañas y ríos con guías certificados.",
                    precio = 350000
                )
            )
            viewModel.insertar(
                Paquete(
                    titulo = "Viaje Cultural Mapuche",
                    descripcion = "Convivencia y aprendizajes con comunidades locales.",
                    precio = 420000
                )
            )
        }
    }

    // --- UI ---
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paquetes disponibles") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(paquetes) { paquete ->
                    PaqueteItem(
                        paquete = paquete,
                        onClick = { onPaqueteClick(paquete.id) }
                    )
                }
            }
        }
    }
}
