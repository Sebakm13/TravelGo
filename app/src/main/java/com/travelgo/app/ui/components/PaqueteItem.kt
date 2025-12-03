package com.travelgo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.travelgo.app.data.Paquete

@Composable
fun PaqueteItem(
    paquete: Paquete,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Cambia 'titulo' por 'nombre' o el nombre correcto en tu modelo
            Text(
                text = paquete.nombre, // Usamos 'nombre' en lugar de 'titulo'
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = paquete.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Precio: $${paquete.precio}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
