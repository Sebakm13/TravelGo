package com.travelgo.app.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import com.travelgo.app.data.datastore.UserPrefsDataStore
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
fun PerfilScreen(
    navController: NavController,
    prefs: UserPrefsDataStore
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("Viajero") }
    var nombreTmp by rememberSaveable { mutableStateOf("") }
    var nombreGuardadoMsg by remember { mutableStateOf(false) }

    var localImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var locationText by rememberSaveable { mutableStateOf("Ubicación no obtenida") }

    // ✅ Cargar el nombre desde DataStore al entrar
    LaunchedEffect(Unit) {
        val savedName = prefs.getName()
        nombre = savedName ?: "Viajero"
        nombreTmp = savedName ?: "Viajero"
    }

    // Lanzador para elegir imagen
    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        localImageUri = uri
    }

    // Lanzador para permisos de ubicación
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            scope.launch {
                val locStr = fetchLocationString(context)
                locationText = locStr ?: "No se pudo obtener ubicación"
            }
        } else {
            locationText = "Permiso denegado"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Tu perfil",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Personaliza tu identidad de viajero 🌍",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(Modifier.height(24.dp))

        // Imagen de perfil
        AsyncImage(
            model = localImageUri
                ?: "https://api.dicebear.com/7.x/bottts/svg?seed=$nombre",
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(20.dp))
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { pickImageLauncher.launch("image/*") },
            shape = MaterialTheme.shapes.large
        ) {
            Text("Cambiar foto")
        }

        Spacer(Modifier.height(24.dp))

        // Campo de nombre editable
        OutlinedTextField(
            value = nombreTmp,
            onValueChange = { nombreTmp = it },
            label = { Text("Nombre para mostrar") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Guardar nombre
        Button(
            onClick = {
                scope.launch {
                    prefs.saveName(nombreTmp.ifBlank { "Viajero" })
                    nombreGuardadoMsg = true
                    nombre = nombreTmp.ifBlank { "Viajero" }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Text("Guardar nombre")
        }

        AnimatedVisibility(visible = nombreGuardadoMsg) {
            Text(
                text = "Nombre guardado ✔",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(Modifier.height(32.dp))

        // Ubicación actual
        Text(
            text = "Ubicación actual",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            },
            shape = MaterialTheme.shapes.large
        ) {
            Text("Obtener ubicación")
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = locationText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "TravelGo SPA • Pagos responsables • Reservas transparentes • Turismo comunitario",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@SuppressLint("MissingPermission")
private suspend fun fetchLocationString(context: Context): String? {
    return try {
        val fused = LocationServices.getFusedLocationProviderClient(context)
        val loc = suspendCancellableCoroutine<Location?> { cont ->
            fused.lastLocation
                .addOnSuccessListener { location -> cont.resume(location) }
                .addOnFailureListener { cont.resume(null) }
        }
        loc?.let { "Lat: ${it.latitude}, Lon: ${it.longitude}" }
    } catch (e: Exception) {
        null
    }
}
