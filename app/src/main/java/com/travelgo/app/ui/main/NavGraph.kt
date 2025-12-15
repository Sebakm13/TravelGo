package com.travelgo.app.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.travelgo.app.data.Repository.ReservaRepository
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.data.db.DatabaseProvider
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.ReservaViewModel
import com.travelgo.app.ui.ReservaViewModelFactory
import com.travelgo.app.ui.screens.*

@Composable
fun TravelNavGraph(
    viewModel: PaqueteViewModel,
    prefs: UserPrefsDataStore,
    navController: NavHostController = rememberNavController()
) {

    NavHost(navController = navController, startDestination = "sustentables") {

        // 🌱 Paquetes sustentables
        composable("sustentables") {
            PaquetesSustentablesScreen(navController)
        }

        // 📦 Mis reservas
        composable("mis_reservas") {
            MisReservasScreen(navController)
        }

        // ✈️ Reserva
        composable(
            route = "reserva/{id}?nombre={nombre}&desc={desc}&precio={precio}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType },
                navArgument("nombre") { type = NavType.StringType; defaultValue = "" },
                navArgument("desc") { type = NavType.StringType; defaultValue = "" },
                navArgument("precio") { type = NavType.FloatType; defaultValue = 0f }
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

        // ✏️ EDITAR RESERVA
        composable(
            route = "editar_reserva/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType }
            )
        ) { backStack ->

            val id = backStack.arguments?.getLong("id") ?: return@composable

            val context = androidx.compose.ui.platform.LocalContext.current
            val repo = ReservaRepository(DatabaseProvider.getDatabase(context).reservaDao())
            val vm: ReservaViewModel = viewModel(factory = ReservaViewModelFactory(repo))

            val reserva = vm.reservas.collectAsState().value.firstOrNull { it.id == id }
                ?: return@composable

            EditarReservaScreen(
                navController = navController,
                reserva = reserva
            )
        }
    }
}
