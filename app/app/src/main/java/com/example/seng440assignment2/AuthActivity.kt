package com.example.seng440assignment2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.seng440assignment2.pages.Login
import com.example.seng440assignment2.pages.Register

import com.example.seng440assignment2.ui.theme.SENG440Assignment2Theme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val mainIntent = Intent(applicationContext, MainActivity::class.java)
        val onLogin = { startActivity(mainIntent) }

        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberAnimatedNavController()

            SENG440Assignment2Theme {
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
                        ) { Login(onLogin, { navController.navigate("register") }) }
                        composable(
                            route = "register",
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) { Register { navController.navigate("login") } }
                    }
                }
            }
        }
    }
}
