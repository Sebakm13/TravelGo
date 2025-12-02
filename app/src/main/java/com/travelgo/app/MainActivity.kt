package com.travelgo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.travelgo.app.data.datastore.UserPrefsDataStore
import com.travelgo.app.navigation.Navigation
import com.travelgo.app.ui.theme.TravelGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val prefs = remember { UserPrefsDataStore(applicationContext) }

            TravelGoTheme {
                Navigation(
                    navController = navController,
                    prefs = prefs
                )
            }
        }
    }
}
