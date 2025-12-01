package com.travelgo.app.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.travelgo.app.data.model.PaqueteTuristico
import com.travelgo.app.data.model.demoPaquetes
import com.travelgo.app.ui.components.TopBarWithBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaquetesScreen(navController: NavController) {

    
    var searchQuery by remember { mutableStateOf("") }
    var selectedDestino by remember { mutableStateOf("Todos") }
    var selectedPrecio by remember { mutableStateOf("Todos") }
    var destinoMenuExpanded by remember { mutableStateOf(false) }
    var precioMenuExpanded by remember { mutableStateOf(false) }

    val destinos = listOf("Todos") + demoPaquetes.map { it.destino }.distinct()

    val precioOptions = listOf(
        "Todos",
        "Hasta $300.000",
        "$300.000 - $400.000",
        "Más de $400.000"
    )

  
    val filteredPaquetes = demoPaquetes.filter { paquete ->

        val matchesName = paquete.titulo.contains(searchQuery, ignoreCase = true)

        val matchesDestino = selectedDestino == "Todos" || paquete.destino == selectedDestino

        val matchesPrecio = when (selectedPrecio) {
            "Hasta $300.000" -> paquete.precioPorPersona <= 300_000
            "$300.000 - $400.000" -> paquete.precioPorPersona in 300_000..400_000
            "Más de $400.000" -> paquete.precioPorPersona > 400_000
            else -> true
        }

        matchesName && matchesDestino && matchesPrecio
    }

    Scaffold(
        topBar = { TopBarWithBack(navController, title = "Paquetes sustentables") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = "Explorar más",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            Text(
                text = "Explora viajes responsables 🌿",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Filtra por destino, precio o nombre del paquete.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            )

            Spacer(Modifier.height(16.dp))

           
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Buscar por nombre") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            Spacer(Modifier.height(12.dp))

            
            
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                // Destino
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { destinoMenuExpanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Destino: $selectedDestino")
                    }
                    DropdownMenu(
                        expanded = destinoMenuExpanded,
                        onDismissRequest = { destinoMenuExpanded = false }
                    ) {
                        destinos.forEach { destino ->
                            DropdownMenuItem(
                                text = { Text(destino) },
                                onClick = {
                                    selectedDestino = destino
                                    destinoMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // Precio
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { precioMenuExpanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Precio: $selectedPrecio")
                    }
                    DropdownMenu(
                        expanded = precioMenuExpanded,
                        onDismissRequest = { precioMenuExpanded = false }
                    ) {
                        precioOptions.forEach { precio ->
                            DropdownMenuItem(
                                text = { Text(precio) },
                                onClick = {
                                    selectedPrecio = precio
                                    precioMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

           
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                items(filteredPaquetes) { paquete ->
                    PaqueteCard(paquete)
                }
            }
        }
    }
}

@Composable
private fun PaqueteCard(paquete: PaqueteTuristico) {
    var pressed by remember { mutableStateOf(false) }
    val cardColor by animateColorAsState(
        targetValue = if (pressed)
            MaterialTheme.colorScheme.surfaceVariant
        else
            MaterialTheme.colorScheme.surface
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { pressed = true }
            .padding(0.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            AsyncImage(
                model = paquete.imagenUrl,
                contentDescription = paquete.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(18.dp))
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = paquete.titulo,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Text(
                text = paquete.destino,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = paquete.descripcion,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )

            Spacer(Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Desde $${paquete.precioPorPersona} CLP por persona",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                )

                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = paquete.enfoqueSustentable,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
