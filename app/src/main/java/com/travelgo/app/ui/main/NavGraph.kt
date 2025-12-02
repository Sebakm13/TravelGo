package com.travelgo.app.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.screens.PaqueteListScreen
import com.travelgo.app.ui.screens.PaqueteDetailScreen
import com.travelgo.app.ui.screens.PaqueteEditScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    prefs: UserPrefsDataStore
) {
    NavHost(
        navController = navController,
        startDestination = "paquetes"
    ) {

        // 📌 LISTA DE PAQUETES
        composable("paquetes") {
            PaqueteListScreen(
                onPaqueteClick = { id ->
                    navController.navigate("detalle/$id")
                },
                onAddClick = {
                    navController.navigate("editar/0")
                }
            )
        }

        // 📌 DETALLE DE PAQUETE
        composable("detalle/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0

            PaqueteDetailScreen(
                paqueteId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("editar/$id") }
            )
        }

        // 📌 EDITAR / CREAR PAQUETE
        composable("editar/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0

            PaqueteEditScreen(
                paqueteId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
