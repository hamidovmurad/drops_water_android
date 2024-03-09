package com.app.drops_water

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.drops_water.navigations.Route
import com.app.drops_water.presentation.analysis.AnalysisScreen
import com.app.drops_water.presentation.greetings.GreetingScreen
import com.app.drops_water.presentation.main.MainScreen
import com.app.drops_water.presentation.settings.SettingsScreen
import com.app.drops_water.ui.theme.Drops_water_androidTheme
import com.app.drops_water.navigations.navigate


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Drops_water_androidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Route.GREETING
                    ){
                        composable(Route.GREETING){
                            GreetingScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.MAIN){
                            MainScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.SETTINGS){
                            SettingsScreen { navController.popBackStack() }
                        }
                        composable(Route.ANALYSIS){
                            AnalysisScreen{ navController.popBackStack() }
                        }
                    }
                }
            }
        }
    }
}

