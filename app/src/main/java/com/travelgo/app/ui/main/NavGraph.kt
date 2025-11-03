package com.travelgo.app.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.screens.PaqueteDetailScreen
import com.travelgo.app.ui.screens.PaqueteEditScreen
import com.travelgo.app.ui.screens.PaqueteListScreen

@Composable
fun TravelNavGraph(viewModel: PaqueteViewModel, navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "list") {

        composable("list") {
            PaqueteListScreen(
                viewModel = viewModel,
                onAdd = { navController.navigate("edit") },
                onOpen = { id -> navController.navigate("detail/$id") }
            )
        }

        composable("detail/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")?.toLongOrNull() ?: return@composable
            PaqueteDetailScreen(
                id = id,
                viewModel = viewModel,
                onEdit = { navController.navigate("edit/$id") },
                onBack = { navController.popBackStack() }
            )
        }

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
                editId = idArg,
                viewModel = viewModel,
                onDone = { navController.popBackStack() }
            )
        }

        // Para crear nuevo paquete
        composable("edit") {
            PaqueteEditScreen(
                editId = null,
                viewModel = viewModel,
                onDone = { navController.popBackStack() }
            )
        }
    }
}