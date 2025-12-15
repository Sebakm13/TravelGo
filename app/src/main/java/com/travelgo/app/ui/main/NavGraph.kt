package com.travelgo.app.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.screens.*

@Composable
fun TravelNavGraph(
    viewModel: PaqueteViewModel,
    prefs: UserPrefsDataStore,
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = "sustentables"
    ) {

        // ===============================
        // 🌱 PAQUETES SUSTENTABLES
        // ===============================
        composable("sustentables") {
            PaquetesSustentablesScreen(navController)
        }

        // ===============================
        // 📦 LISTA PAQUETES (ROOM)
        // ===============================
        composable("list") {
            PaqueteListScreen(
                viewModel = viewModel,
                onAdd = { navController.navigate("edit") },
                onOpen = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }

        // ===============================
        // 🔍 DETALLE PAQUETE
        // ===============================
        composable(
            route = "detail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType }
            )
        ) { backStack ->
            val id = backStack.arguments?.getLong("id") ?: 0L

            PaqueteDetailScreen(
                navController = navController,
                id = id,
                onReservar = {
                    navController.navigate("reserva/$id")
                },
                onEdit = {
                    navController.navigate("edit/$id")
                }
            )
        }

        // ===============================
        // ✏️ EDITAR / CREAR PAQUETE
        // ===============================
        composable(
            route = "edit/{id}",
            arguments = listOf(
                navArgument("id") {
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStack ->
            val idArg = backStack.arguments?.getString("id")?.toLongOrNull()

            PaqueteEditScreen(
                navController = navController,
                editId = idArg,
                viewModel = viewModel,
                onDone = { navController.popBackStack() }
            )
        }

        composable("edit") {
            PaqueteEditScreen(
                navController = navController,
                editId = null,
                viewModel = viewModel,
                onDone = { navController.popBackStack() }
            )
        }

        // ===============================
        // 🧾 MIS RESERVAS
        // ===============================
        composable("mis_reservas") {
            MisReservasScreen(navController = navController)
        }

        // ===============================
        // ✈️ RESERVA (DESDE SUSTENTABLES)
        // ===============================
        composable(
            route = "reserva/{id}?nombre={nombre}&desc={desc}&precio={precio}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType },
                navArgument("nombre") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("desc") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("precio") {
                    type = NavType.FloatType
                    defaultValue = 0f
                }
            )
        ) { backStack ->

            val id = backStack.arguments?.getLong("id") ?: 0L
            val nombre = backStack.arguments?.getString("nombre").orEmpty()
            val desc = backStack.arguments?.getString("desc").orEmpty()
            val precio = backStack.arguments?.getFloat("precio")?.toDouble() ?: 0.0

            ReservaScreen(
                navController = navController,
                paqueteId = id,
                nombreArg = nombre,
                descArg = desc,
                precioArg = precio
            )
        }
    }
}
