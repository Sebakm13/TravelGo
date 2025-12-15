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
        PaqueteSustentableUI(
            201,
            "Torres del Paine",
            "Parque nacional con senderos y turismo sustentable.",
            250000,
            "https://lh3.googleusercontent.com/gps-cs-s/AG0ilSw_j4uC1kE5VGsMlOC-uWIdkv8lRK3LzuRtj5Bwah71BCn3izKxCBbk_4H1Y5pMpdsGEbcfB0qeZhMBgVwi35-PJ_XjhRzdFjMblwOhneUO2L5NXLv1yd0LkqPEKjQIgdadrSHT6A=s680-w680-h510-rw"
        ),
        PaqueteSustentableUI(
            202,
            "San Pedro de Atacama",
            "Desierto, géiseres y energía limpia.",
            180000,
            "https://www.diarioantofagasta.cl/wp-content/uploads/2021/09/San-Pedro-De-Atacama-1024x684.jpg"
        ),
        PaqueteSustentableUI(
            203,
            "Isla de Pascua",
            "Cultura ancestral y protección ambiental.",
            320000,
            "https://www.tangol.com/Blog/Fotos/que-hacer-en-isla-de-pascua_0_201711131023220-resized.webp"
        ),
        PaqueteSustentableUI(
            204,
            "Valle del Elqui",
            "Astroturismo y pueblos ecológicos.",
            140000,
            "https://storage.googleapis.com/chile-travel-cdn/2021/07/Valle-del-Elqui_5.jpg"
        ),
        PaqueteSustentableUI(
            205,
            "Chiloé",
            "Palafitos, cultura chilota y naturaleza.",
            160000,
            "https://www.skorpios.cl/wp-content/uploads/Isla-de-Chilo%C3%A9-780x400.jpg"
        )
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
                                    navController.navigate(
                                        "reserva/${lugar.id}" +
                                                "?nombre=${Uri.encode(lugar.nombre)}" +
                                                "&desc=${Uri.encode(lugar.descripcion)}" +
                                                "&precio=${lugar.precio}"
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
