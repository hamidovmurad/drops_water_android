package com.app.drops_water

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.drops_water.data.preferences.DefaultPreferences
import com.app.drops_water.data.preferences.Preferences
import com.app.drops_water.navigations.Route
import com.app.drops_water.navigations.navigate
import com.app.drops_water.presentation.TrackerViewModel
import com.app.drops_water.presentation.greetings.GreetingScreen
import com.app.drops_water.presentation.main.MainScreen
import com.app.drops_water.presentation.settings.SettingsScreen
import com.app.drops_water.ui.theme.Drops_water_androidTheme

class MainActivity : ComponentActivity() {

    private val preferences by lazy {
        DefaultPreferences(applicationContext.getSharedPreferences(Preferences.PREF_NAME, MODE_PRIVATE))
    }

    private val trackerViewModel by viewModels<TrackerViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TrackerViewModel(this@MainActivity.application,preferences) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            Drops_water_androidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val startDestination = if(trackerViewModel.isSetUp()) Route.MAIN else Route.GREETING

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ){
                        composable(Route.GREETING){
                            GreetingScreen(trackerViewModel,onNavigate = navController::navigate)
                        }
                        composable(Route.MAIN){
                            MainScreen(trackerViewModel,onNavigate = navController::navigate)
                        }
                        composable(Route.SETTINGS){
                            SettingsScreen(trackerViewModel) { navController.popBackStack() }
                        }

                    }
                }
            }
        }
    }
}

