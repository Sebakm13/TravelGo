package com.travelgo.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.travelgo.app.ui.screens.*
import com.travelgo.app.data.datastore.UserPrefsDataStore

@Composable
fun Navigation(navController: NavHostController, prefs: UserPrefsDataStore) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(navController, prefs)
        }

        composable("home") {
            HomeScreen(navController, prefs)
        }

        composable("paquetes") {
            PaquetesScreen(navController)
        }

        composable("profile") {
            PerfilScreen(navController, prefs)
        }
    }
}
