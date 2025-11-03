package com.travelgo.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.data.datastore.UserPrefsDataStore

@Composable
fun HomeScreen(
    navController: NavController,
    prefs: UserPrefsDataStore
) {
    var nombre by remember { mutableStateOf("Viajero") }

    // ✅ Cargar nombre desde DataStore sin bloquear UI
    LaunchedEffect(Unit) {
        val savedName = prefs.getName()
        nombre = savedName ?: "Viajero"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Hola, $nombre 👋",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(4.dp))
        Text(
            text = "Explora experiencias con impacto positivo 🌍",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(Modifier.height(24.dp))

        // 🌱 Tarjeta 1: Paquetes sustentables
        FeatureCard(
            title = "Paquetes sustentables",
            desc = "Descubre viajes con economía local y bajo impacto ambiental",
            accent = MaterialTheme.colorScheme.primary,
            onClick = { navController.navigate("paquetes") }
        )

        Spacer(Modifier.height(16.dp))

        // 🧭 Tarjeta 2: Reservar experiencia
        FeatureCard(
            title = "Reservar experiencia",
            desc = "Gestiona tu reserva rápida y segura",
            accent = MaterialTheme.colorScheme.secondary,
            onClick = { navController.navigate("reserva") }
        )

        Spacer(Modifier.height(16.dp))

        // 👤 Tarjeta 3: Tu perfil
        FeatureCard(
            title = "Tu perfil",
            desc = "Foto, ubicación actual y datos de contacto",
            accent = MaterialTheme.colorScheme.tertiary,
            onClick = { navController.navigate("perfil") }
        )

        Spacer(Modifier.height(32.dp))

        AnimatedVisibility(visible = true) {
            Text(
                text = "TravelGo SPA • Turismo comunitario y movilidad responsable",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    desc: String,
    accent: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(20.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Box(
                modifier = Modifier
                    .background(
                        color = accent.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = accent,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(12.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}
