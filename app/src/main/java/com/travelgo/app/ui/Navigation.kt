package com.travelgo.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.screens.*

@Composable
fun Navigation(
    navController: NavHostController,
    prefs: UserPrefsDataStore,
    viewModel: PaqueteViewModel // Pasar el ViewModel aquí
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // ---------- AUTH ----------
        composable("login") {
            LoginScreen(navController = navController, prefs = prefs)
        }

        composable("register") {
            RegisterScreen(navController = navController, prefs = prefs)
        }

        // ---------- HOME ----------
        composable("home") {
            HomeScreen(navController = navController, prefs = prefs)
        }

        // ---------- PAQUETES DEMO ----------
        composable("paquetes") {
            PaquetesScreen(navController = navController)
        }

        // ---------- PERFIL ----------
        composable("perfil") {
            PerfilScreen(navController = navController, prefs = prefs) // Pasar navController
        }

        // ---------- RESERVA ----------
        composable("reserva") {
            ReservaScreen(navController = navController)
        }

        // ---------- PAQUETES CRUD ----------
        composable("paquetesCrud") {
            PaqueteListScreen(
                viewModel = viewModel,
                onAdd = { navController.navigate("paqueteEditar/0") },
                onOpen = { id -> navController.navigate("paqueteDetalle/$id") }
            )
        }

        composable("paqueteDetalle/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0

            PaqueteDetailScreen(
                id = id,
                viewModel = viewModel,
                onEdit = { navController.navigate("paqueteEditar/$id") },
                navController = navController // Pasar navController
            )
        }

        composable("paqueteEditar/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0

            PaqueteEditScreen(
                navController = navController,
                editId = id,
                viewModel = viewModel,
                onDone = { navController.popBackStack() }
            )
        }
    }
}
