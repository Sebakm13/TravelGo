package com.travelgo.app.ui.screens

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

data class PaqueteSustentableUI(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val imagenUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaquetesSustentablesScreen(
    navController: NavController
) {

    val lugares = listOf(
        PaqueteSustentableUI(201, "Torres del Paine", "Parque nacional con senderos y turismo sustentable.", 250000,
            "https://images.unsplash.com/photo-1501785888041-af3ef285b470"),
        PaqueteSustentableUI(202, "San Pedro de Atacama", "Desierto, géiseres y energía limpia.", 180000,
            "https://images.unsplash.com/photo-1544986581-efac024faf62"),
        PaqueteSustentableUI(203, "Isla de Pascua", "Cultura ancestral y protección ambiental.", 320000,
            "https://images.unsplash.com/photo-1587502536263-9298c53a2d8c"),
        PaqueteSustentableUI(204, "Valle del Elqui", "Astroturismo y pueblos ecológicos.", 140000,
            "https://images.unsplash.com/photo-1616512659458-6c7f1a3c1e1c"),
        PaqueteSustentableUI(205, "Chiloé", "Palafitos, cultura chilota y naturaleza.", 160000,
            "https://images.unsplash.com/photo-1596495577886-d920f1fb7238")
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Paquetes Sustentables 🌱") }) }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(lugares) { lugar ->
                Card(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Column {

                        AsyncImage(
                            model = lugar.imagenUrl,
                            contentDescription = lugar.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clickable {
                                    val nombre = Uri.encode(lugar.nombre)
                                    val desc = Uri.encode(lugar.descripcion)
                                    val img = Uri.encode(lugar.imagenUrl)

                                    navController.navigate(
                                        "reserva/${lugar.id}" +
                                                "?nombre=${Uri.encode(lugar.nombre)}" +
                                                "&desc=${Uri.encode(lugar.descripcion)}" +
                                                "&precio=${lugar.precio.toDouble()}"
                                    )


                                }
                        )

                        Column(Modifier.padding(16.dp)) {
                            Text(lugar.nombre, style = MaterialTheme.typography.titleLarge)
                            Text(lugar.descripcion)
                            Spacer(Modifier.height(4.dp))
                            Text("Precio: $${lugar.precio} CLP")
                        }
                    }
                }
            }
        }
    }
}
