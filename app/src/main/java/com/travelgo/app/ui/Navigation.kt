package com.travelgo.app.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.screens.*

@Composable
fun Navigation(
    navController: NavHostController,
    prefs: UserPrefsDataStore,
    viewModel: PaqueteViewModel
)
 {
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

        // ---------- PAQUETES (FLUJO REAL) ----------
        composable("paquetes") {
            PaquetesScreen(
                navController = navController,
            )
        }


        // ---------- PERFIL ----------
        composable("perfil") {
            PerfilScreen(navController = navController, prefs = prefs)
        }

        // ---------- DETALLE ----------
        composable(
            route = "paqueteDetalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable

            PaqueteDetailScreen(
                navController = navController,
                id = id,
                onReservar = {
                    navController.navigate("reserva/$id")
                },
                onEdit = {
                    navController.navigate("paqueteEditar/$id")
                }
            )
        }

        // ---------- RESERVA ----------
        composable(
            route = "reserva/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable

            ReservaScreen(
                navController = navController,
                paqueteId = id,
                nombreArg = TODO(),
                descArg = TODO(),
                precioArg = TODO()
            )
        }

        // ---------- EDITAR ----------
        composable(
            route = "paqueteEditar/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L

            PaqueteEditScreen(
                navController = navController,
                editId = id,
                viewModel = viewModel,
                onDone = { navController.popBackStack() }
            )
        }
    }
}
