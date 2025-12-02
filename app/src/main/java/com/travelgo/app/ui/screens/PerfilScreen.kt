package com.travelgo.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.travelgo.app.R
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.components.TopBarWithBack
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    prefs: UserPrefsDataStore
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var nombre by rememberSaveable { mutableStateOf("Viajero") }
    var editandoNombre by remember { mutableStateOf(false) }
    var localImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    // Cargar datos guardados
    LaunchedEffect(Unit) {
        nombre = prefs.getName() ?: "Viajero"
        prefs.getPhoto()?.let { localImageUri = Uri.parse(it) }
    }

    // Selector de imagen
    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            localImageUri = uri
            scope.launch { prefs.savePhoto(uri.toString()) }
        }
    }

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = "Mi Perfil",
                onBack = { navController.popBackStack() }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(20.dp))

            // FOTO CON BORDE DEGRADADO
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        ),
                        shape = CircleShape
                    )
                    .padding(5.dp)
                    .clip(CircleShape),

                contentAlignment = Alignment.BottomEnd
            ) {

                AsyncImage(
                    model = localImageUri
                        ?: ImageRequest.Builder(context)
                            .data(R.drawable.img)
                            .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxSize()
                )

                // Botón cambiar foto
                Surface(
                    modifier = Modifier
                        .size(42.dp)
                        .offset((-10).dp, (-10).dp)
                        .clip(CircleShape)
                        .clickable { pickImageLauncher.launch("image/*") },
                    color = MaterialTheme.colorScheme.primary,
                    shadowElevation = 6.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Foto",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))


            // NOMBRE
            if (!editandoNombre) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = nombre,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(Modifier.width(8.dp))

                    IconButton(onClick = { editandoNombre = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Tu nombre") }
                    )

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = {
                            editandoNombre = false
                            scope.launch { prefs.saveName(nombre) }
                        }
                    ) {
                        Text("Guardar")
                    }
                }
            }

            Spacer(Modifier.height(10.dp))
            Text(
                text = "Viajero de corazón 🌍",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(Modifier.height(20.dp))

            Divider()

            Spacer(Modifier.height(20.dp))
        }
    }
}
