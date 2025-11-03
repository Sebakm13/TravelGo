package com.travelgo.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.travelgo.app.data.datastore.UserPrefsDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    prefs: UserPrefsDataStore
) {
    val scope = rememberCoroutineScope()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var loading by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }

    fun validate(): Boolean {
        var ok = true
        if (email.isBlank()) {
            emailError = "El correo es obligatorio"
            ok = false
        } else emailError = null

        if (password.isBlank()) {
            passwordError = "La contraseña es obligatoria"
            ok = false
        } else passwordError = null

        return ok
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "TravelGo",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "Turismo sustentable, comunidad real 🌱",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = emailError != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = emailError != null) {
            Text(
                text = emailError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            isError = passwordError != null,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = passwordError != null) {
            Text(
                text = passwordError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            enabled = !loading,
            onClick = {
                if (!validate()) return@Button

                // Lanza corrutina sin bloquear UI
                scope.launch {
                    try {
                        loading = true
                        loginError = null

                        // Simula login (acepta cualquier usuario)
                        delay(1000)

                        prefs.saveToken("TOKEN_LOCAL_SIMULADO")
                        val nombre = email.substringBefore("@").ifBlank { "Viajero" }
                        prefs.saveName(nombre)

                        loading = false

                        // Navega al Home
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } catch (e: Exception) {
                        loading = false
                        loginError = "Error inesperado"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.large
        ) {
            if (loading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Ingresar")
            }
        }

        AnimatedVisibility(visible = loginError != null) {
            Text(
                text = loginError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
