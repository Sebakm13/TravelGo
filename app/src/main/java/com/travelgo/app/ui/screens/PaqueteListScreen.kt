package com.travelgo.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.travelgo.app.ui.PaqueteViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PaqueteListScreen(
    viewModel: PaqueteViewModel,
    onAdd: () -> Unit,
    onOpen: (Long) -> Unit
) {
    val paquetes by viewModel.paquetes.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Text("+")
            }
        }
    ) { padding ->

        if (paquetes.isEmpty()) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay paquetes aún")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(
                    items = paquetes,
                    key = { it.id }
                ) { paquete ->

                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically()
                    ) {
                        PaqueteItem(
                            paquete = paquete,
                            modifier = Modifier.animateItemPlacement(),
                            onClick = { onOpen(paquete.id) } // 👉 click imagen → detalle/reserva
                        )
                    }
                }
            }
        }
    }
}
