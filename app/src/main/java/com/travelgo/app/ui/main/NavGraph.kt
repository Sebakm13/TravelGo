package com.travelgo.app.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
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
    NavHost(navController, startDestination = "list") {

        composable("list") {
            PaqueteListScreen(
                viewModel = viewModel,
                onAdd = { navController.navigate("edit") },
                onOpen = { id -> navController.navigate("detail/$id") }
            )
        }

        composable("register") {
            RegisterScreen(navController = navController, prefs = prefs)
        }

        composable("detail/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")?.toLongOrNull() ?: return@composable
            PaqueteDetailScreen(
                navController = navController,
                id = id,
                viewModel = viewModel,
                onEdit = { navController.navigate("edit/$id") }
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
        composable("crud") {
            PaqueteCrudScreen(navController)
        }

    }
}