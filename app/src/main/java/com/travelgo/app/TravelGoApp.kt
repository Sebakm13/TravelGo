package com.travelgo.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.screens.HomeScreen

@Composable
fun TravelGoApp(prefs: UserPrefsDataStore) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("home") { HomeScreen(navController, prefs) }
    }
}