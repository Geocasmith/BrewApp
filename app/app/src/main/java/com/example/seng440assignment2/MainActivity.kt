package com.example.seng440assignment2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seng440assignment2.datastore.AppSettingsSerializer
import com.example.seng440assignment2.navigation.AnimatedNav
import com.example.seng440assignment2.navigation.AnimatedNavBar
import com.example.seng440assignment2.ui.theme.SENG440Assignment2Theme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

val Context.settingsDataStore by dataStore(
    fileName = "app_settings.json",
    serializer = AppSettingsSerializer
)


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // View Model
            val owner = LocalViewModelStoreOwner.current

            val viewModel: MainViewModel = owner?.let {
                viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(settingsDataStore)
                )
            }!!

            SENG440Assignment2Theme(darkTheme = viewModel.getAppSettings().isDarkMode) {

                val navController = rememberAnimatedNavController()

                Scaffold(
                    bottomBar = { AnimatedNavBar(navController = navController) }
                ) {
                        paddingValues ->
                    AnimatedNav(navController = navController, mainViewModel = viewModel, padding = paddingValues)
                }
            }
        }
    }
}
