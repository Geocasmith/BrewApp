package com.example.seng440assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.seng440assignment2.datastore.UserPreferencesSerializer
import com.example.seng440assignment2.navigation.AnimatedNav
import com.example.seng440assignment2.navigation.AnimatedNavBar
import com.example.seng440assignment2.ui.theme.SENG440Assignment2Theme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SENG440Assignment2Theme {

                val navController = rememberAnimatedNavController()

                // View Model
                val owner = LocalViewModelStoreOwner.current

                Scaffold(
                    bottomBar = { AnimatedNavBar(navController = navController) }
                ) {
                        paddingValues -> owner?.let {
                    AnimatedNav(navController = navController, padding = paddingValues)
                }
                }
            }
        }
    }
}
