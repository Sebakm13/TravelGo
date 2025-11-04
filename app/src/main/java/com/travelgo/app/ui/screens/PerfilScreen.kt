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
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.travelgo.app.ui.components.ImagePickerDialog
import com.travelgo.app.viewmodel.ProfileUiState
import com.travelgo.app.viewmodel.ProfileViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.layout.Column as Column

private fun createImageUri(context: Context): Uri? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "profile_avatar_$timeStamp.jpg"
    val storageDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)

    return try {
        val imageFile = File(storageDir, imageFileName)
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    } catch (e: Exception) {
        null
    }
}


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

    LaunchedEffect(Unit) {
        val savedName = prefs.getName()
        nombre = savedName ?: "Viajero"
        nombreTmp = savedName ?: "Viajero"
    }

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

        OutlinedTextField(
            value = nombreTmp,
            onValueChange = { nombreTmp = it },
            label = { Text("Nombre para mostrar") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

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

@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    onRefresh: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = viewModel()
    var showImagePicker by remember { mutableStateOf(false) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Launchers
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            viewModel.updateAvatar(tempCameraUri)
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.updateAvatar(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    // Avatar principal
                    if (uiState.avatarUri != null) {
                        AsyncImage(
                            model = uiState.avatarUri,
                            contentDescription = "Avatar del usuario",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .clickable { showImagePicker = true }
                                .background(MaterialTheme.colorScheme.primary),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { showImagePicker = true },
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Filled.Person,
                                contentDescription = "Seleccionar avatar",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(28.dp)
                            )
                        }
                    }

                    // Icono de cámara en esquina
                    Surface(
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { showImagePicker = true },
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = 2.dp
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.CameraAlt,
                            contentDescription = "Cambiar foto",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre
                Text(
                    text = uiState.user?.name ?: "",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Email
                Text(
                    text = uiState.user?.email ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    // Diálogo de selección de imagen
    if (showImagePicker) {
        ImagePickerDialog(
            onDismiss = { showImagePicker = false },
            onCameraClick = {
                showImagePicker = false
                if (isPermissionGranted(context, Manifest.permission.CAMERA)) {
                    tempCameraUri = createImageUri(context)
                    tempCameraUri?.let { takePictureLauncher.launch(it) }
                } else {
                    takePictureLauncher.launch(null) // pedimos permiso
                }
            },
            onGalleryClick = {
                showImagePicker = false
                val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }

                if (isPermissionGranted(context, imagePermission)) {
                    pickImageLauncher.launch("image/*")
                } else {
                    pickImageLauncher.launch("image/*") // pedimos permiso
                }
            }
        )
    }
}

// Función para chequear permisos
private fun isPermissionGranted(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(context, permission) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
}