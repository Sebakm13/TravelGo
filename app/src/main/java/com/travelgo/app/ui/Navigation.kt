package com.travelgo.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.screens.*

@Composable
fun Navigation(
    navController: NavHostController,
    prefs: UserPrefsDataStore
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // ---------- AUTH ----------
        composable("login") {
            LoginScreen(navController, prefs)
        }

        composable("register") {
            RegisterScreen(navController, prefs)
        }

        // ---------- HOME ----------
        composable("home") {
            HomeScreen(navController, prefs)
        }

        // ---------- PAQUETES DEMO (con imágenes y filtros) ----------
        composable("paquetes") {
            PaquetesScreen(navController)
        }

        // ---------- PERFIL ----------
        composable("perfil") {
            PerfilScreen(navController, prefs)
        }

        // ---------- RESERVA ----------
        composable("reserva") {
            ReservaScreen(navController)
        }

        // ---------- PAQUETES CRUD (ROOM) ----------
        composable("paquetesCrud") {
            PaqueteListScreen(
                onPaqueteClick = { id ->
                    navController.navigate("paqueteDetalle/$id")
                },
                onAddClick = {
                    navController.navigate("paqueteEditar/0")
                }
            )
        }

        composable("paqueteDetalle/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0

            PaqueteDetailScreen(
                paqueteId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("paqueteEditar/$id") }
            )
        }

        composable("paqueteEditar/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0

            PaqueteEditScreen(
                paqueteId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
