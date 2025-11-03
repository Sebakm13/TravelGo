package com.travelgo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.screens.HomeScreen
import com.travelgo.app.ui.screens.LoginScreen
import com.travelgo.app.ui.screens.PaquetesScreen
import com.travelgo.app.ui.screens.PerfilScreen
import com.travelgo.app.ui.screens.ReservaScreen
import com.travelgo.app.ui.theme.TravelGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val prefs = remember { UserPrefsDataStore(this) }
            val navController = rememberNavController()

            TravelGoTheme {
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            navController = navController,
                            prefs = prefs
                        )
                    }
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            prefs = prefs
                        )
                    }
                    composable("paquetes") {
                        PaquetesScreen(
                            navController = navController
                        )
                    }
                    composable("reserva") {
                        ReservaScreen(
                            navController = navController
                        )
                    }
                    composable("perfil") {
                        PerfilScreen(
                            navController = navController,
                            prefs = prefs
                        )
                    }
                }
            }
        }
    }
}
