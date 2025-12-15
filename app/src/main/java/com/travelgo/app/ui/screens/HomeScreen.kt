package com.travelgo.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.data.datastore.UserPrefsDataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    prefs: UserPrefsDataStore
) {
    val scope = rememberCoroutineScope()
    var nombre by remember { mutableStateOf("Viajero") }

    LaunchedEffect(Unit) {
        prefs.getName()?.let { nombre = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TravelGo 🌎", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    TextButton(onClick = {
                        scope.launch {
                            prefs.clear()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    }) {
                        Text("Cerrar sesión", color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                        )
                    )
                )
                .padding(24.dp)
        ) {

            Text(
                text = "Hola, $nombre 👋",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(28.dp))

            FeatureCard(
                icon = Icons.Default.FlightTakeoff,
                title = "Paquetes sustentables",
                desc = "Viajes con impacto positivo.",
                accent = MaterialTheme.colorScheme.primary,
                onClick = {
                    // ✅ CAMBIO CLAVE (YA NO CRASHEA)
                    navController.navigate("paquetes")
                }
            )

            Spacer(Modifier.height(18.dp))

            FeatureCard(
                icon = Icons.Default.LocalActivity,
                title = "Reservas",
                desc = "Revisa y gestiona tus reservas.",
                accent = MaterialTheme.colorScheme.secondary,
                onClick = {
                    navController.navigate("mis_reservas") // ✅ VA A LAS RESERVAS
                }
            )


            Spacer(Modifier.height(18.dp))

            FeatureCard(
                icon = Icons.Default.AccountCircle,
                title = "Tu perfil",
                desc = "Configura tu cuenta.",
                accent = MaterialTheme.colorScheme.tertiary,
                onClick = { navController.navigate("perfil") }
            )
        }
    }
}

@Composable
private fun FeatureCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String,
    accent: Color,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.97f else 1f)
    val bgColor by animateColorAsState(
        if (pressed) accent.copy(alpha = 0.15f)
        else MaterialTheme.colorScheme.surface
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                pressed = true
                onClick()
                pressed = false
            },
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = accent, modifier = Modifier.size(40.dp))
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.SemiBold, color = accent)
                Text(desc, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
