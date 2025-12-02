package com.travelgo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.ui.PaqueteViewModel
import com.travelgo.app.ui.PaqueteViewModelFactory
import com.travelgo.app.ui.main.TravelNavGraph
import com.travelgo.app.ui.screens.*
import com.travelgo.app.ui.theme.TravelGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val prefs = remember { UserPrefsDataStore(context) }
            val viewModel: PaqueteViewModel = viewModel(factory = PaqueteViewModelFactory(prefs))
            val navController = rememberNavController()

            TravelGoTheme {
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(navController = navController, prefs = prefs)
                    }
                    composable("register") {
                        RegisterScreen(navController = navController, prefs = prefs)
                    }
                    composable("home") {
                        HomeScreen(navController = navController, prefs = prefs)
                    }

                    composable("paquetes") {
                        TravelNavGraph(
                            viewModel = viewModel,
                            prefs = prefs
                        )
                    }
                    composable("reserva") {
                        ReservaScreen(navController = navController)
                    }
                    composable("perfil") {
                        PerfilScreen(navController = navController, prefs = prefs)
                    }
                }
            }
        }
    }
}
