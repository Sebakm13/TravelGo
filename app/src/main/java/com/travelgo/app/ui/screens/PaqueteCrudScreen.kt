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
import androidx.navigation.NavController
import com.travelgo.app.data.Repository.PaqueteRepository
import com.travelgo.app.data.db.DatabaseProvider
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.PaqueteViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaqueteCrudScreen(navController: NavController) {

    // ========== ViewModel con Factory ==========
    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val dao = db.paqueteLocal()
    val repo = PaqueteRepository(dao)

    val viewModel: PaqueteViewModel = viewModel(
        factory = PaqueteViewModelFactory(repo)
    )
    // ===========================================

    val paquetes by viewModel.paquetes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Administrar Paquetes") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(paquetes) { p ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(p.nombre, style = MaterialTheme.typography.titleLarge)
                            Text(p.descripcion, style = MaterialTheme.typography.bodyMedium)
                            Text("$${p.precio}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
