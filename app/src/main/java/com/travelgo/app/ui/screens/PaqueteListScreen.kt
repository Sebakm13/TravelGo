package com.travelgo.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.components.TopBarWithBack
import kotlinx.coroutines.flow.forEach

@Composable
fun PaqueteListScreen(
    viewModel: PaqueteViewModel,
    onAdd: () -> Unit,
    onOpen: (Long) -> Unit
) {
    val paquetes by viewModel.paquetes.collectAsState()  // ← ESTA ES LA FORMA CORRECTA

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            paquetes.forEach { paquete ->
                Text(
                    text = "${paquete.nombre} — $${paquete.precio}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpen(paquete.id) }
                        .padding(16.dp)
                )
                Divider()
            }
        }
    }
}
