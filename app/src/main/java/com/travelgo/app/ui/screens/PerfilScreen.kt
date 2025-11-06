package com.travelgo.app.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.components.TopBarWithBack
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

    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        localImageUri = uri
    }

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

    LaunchedEffect(Unit) {
        val savedName = prefs.getName()
        nombre = savedName ?: "Viajero"
        nombreTmp = savedName ?: "Viajero"
    }

    Scaffold(
        topBar = { TopBarWithBack(navController, title = "Mi Perfil") }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🧍 Imagen de perfil
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AsyncImage(
                        model = localImageUri
                            ?: "https://api.dicebear.com/7.x/bottts/svg?seed=$nombre",
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                    // Cámara flotante
                    Surface(
                        modifier = Modifier
                            .offset(x = (-6).dp, y = (-6).dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable { pickImageLauncher.launch("image/*") },
                        color = MaterialTheme.colorScheme.primary,
                        shadowElevation = 4.dp
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Cambiar foto",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = nombre,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "Viajero de corazón 🌍",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )

                Spacer(Modifier.height(20.dp))

                // 🧾 Campo de nombre
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = nombreTmp,
                            onValueChange = { nombreTmp = it },
                            label = { Text("Nombre para mostrar") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                scope.launch {
                                    prefs.saveName(nombreTmp.ifBlank { "Viajero" })
                                    nombre = nombreTmp.ifBlank { "Viajero" }
                                    nombreGuardadoMsg = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
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
                    }
                }

                Spacer(Modifier.height(24.dp))

                // 📍 Ubicación
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Tu ubicación actual:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = locationText,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Obtener ubicación")
                        }
                    }
                }

                Spacer(Modifier.height(32.dp))

                Text(
                    text = "🌎 TravelGo — Viaja. Descubre. Disfruta.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
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
        loc?.let { "Lat: %.4f, Lon: %.4f".format(it.latitude, it.longitude) }
    } catch (e: Exception) {
        null
    }
}
