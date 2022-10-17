package com.example.seng440assignment2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seng440assignment2.datastore.AppSettingsSerializer
import com.example.seng440assignment2.datastore.UserData
import com.example.seng440assignment2.datastore.UserDataSerializer
import com.example.seng440assignment2.pages.Login
import com.example.seng440assignment2.pages.Register

import com.example.seng440assignment2.ui.theme.SENG440Assignment2Theme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.builtins.serializer

val Context.userDataStore by dataStore(
    fileName = "user_data.json",
    serializer = UserDataSerializer
)

@OptIn(ExperimentalAnimationApi::class)
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val mainIntent = Intent(applicationContext, MainActivity::class.java)
        val onLogin = {
            startActivity(mainIntent)
            finish()
        }

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            val owner = LocalViewModelStoreOwner.current

            val viewModel: AuthViewModel = owner?.let {
                viewModel(
                    it,
                    "MainViewModel",
                    AuthViewModelFactory(userDataStore, LocalContext.current)
                )
            }!!

            val userData = viewModel.getUserData()

            SENG440Assignment2Theme {

                if (userData.authToken != "") {
                    onLogin()
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "login",
                    ) {
                        composable(
                            route = "login",
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) { Login(viewModel, onLogin, { navController.navigate("register") }) }
                        composable(
                            route = "register",
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) {
                            Register(viewModel, { navController.navigate("login") })
                        }
                    }
                }
            }
        }
    }
}
