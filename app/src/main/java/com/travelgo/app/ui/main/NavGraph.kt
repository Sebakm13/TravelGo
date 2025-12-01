package com.travelgo.app.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.screens.PaqueteDetailScreen
import com.travelgo.app.ui.screens.PaqueteEditScreen
import com.travelgo.app.ui.screens.PaqueteListScreen

@Composable
fun TravelNavGraph(
    viewModel: PaqueteViewModel,
    prefs: UserPrefsDataStore,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "list"
    ) {

        composable("list") {
            PaqueteListScreen(
                navController = navController,
                viewModel = viewModel,
                onAdd = { navController.navigate("edit") },
                onOpen = { id -> navController.navigate("detail/$id") }
            )
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(
                navArgument("id") { nullable = false }
            )
        ) { backStack ->
            val id = backStack.arguments?.getString("id")?.toLongOrNull() ?: return@composable

            PaqueteDetailScreen(
                navController = navController,
                id = id,
                viewModel = viewModel,
                onEdit = { navController.navigate("edit/$id") }
            )
        }

        composable("edit") {
            PaqueteEditScreen(
                navController = navController,
                viewModel = viewModel,
                id = null
            )
        }

        composable(
            route = "edit/{id}",
            arguments = listOf(
                navArgument("id") { nullable = true }
            )
        ) { backStack ->
            val id = backStack.arguments?.getString("id")?.toLongOrNull()
            PaqueteEditScreen(
                navController = navController,
                viewModel = viewModel,
                id = id
            )
        }
    }
}
