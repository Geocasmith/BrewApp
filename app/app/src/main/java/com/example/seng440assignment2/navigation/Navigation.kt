package com.example.seng440assignment2.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.ProfileScreen
import com.example.seng440assignment2.camera.ScanScreen
import com.example.seng440assignment2.pages.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
fun AnimatedNavBar(navController: NavHostController) {
    val pages = listOf(
        Screen.Review,
        Screen.Search,
        Screen.Scan,
        Screen.Profile
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        pages.forEach { page ->
            NavigationBarItem(
                icon = { Icon(page.icon, contentDescription = null) },
                label = { Text(stringResource(id = page.screenName)) },
                selected = currentDestination?.hierarchy?.any { it.route == page.route } == true,
                onClick = {

                    navController.navigate(page.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}

// Animation Navigation https://google.github.io/accompanist/navigation-animation/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNav(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    padding: PaddingValues,
    onLogout: () -> Unit
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Review.route,
        modifier = Modifier.padding(padding)
    ) {
        composable(
            route = Screen.Review.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) { Reviews(onNavigateToBeerPage = { beerName: String -> navController.navigate("beer/$beerName") }) }

        composable(
            route = Screen.Search.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) { Categories(onNavigateToBeerListPage = { beerType: String -> navController.navigate("beerList/$beerType") }) }
        composable(
            route = Screen.Scan.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            ScanScreen(
                mainViewModel,
                { barcode: String -> navController.navigate("beer/$barcode") },
                { barcode: String -> navController.navigate("new/$barcode") })
        }
        composable(
            route = Screen.Profile.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            ProfileScreen(
                mainViewModel,
                onNavigateToEdit = { navController.navigate("edit") },
                onNavigateToPref = { navController.navigate("pref") },
                onLogout = onLogout
            )
        }
        composable(
            route = "pref",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            PrefScreen(
                mainViewModel,
                onBackButtonPress = { navController.navigate(Screen.Profile.route) })
        }
        composable(
            route = "edit",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            EditScreen(
                mainViewModel = mainViewModel,
                onBackButtonPress = { navController.navigate(Screen.Profile.route) })
        }
        composable(
            route = "beerList/{type}",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) { backStackEntry ->
            BeerListPage(
                backStackEntry.arguments?.getString("type")
            ) { navController.navigate("beerList") }
        }
        composable(
            route = "beer/{barcode}",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) { backStackEntry ->
            BeerPage(
                mainViewModel,
                backStackEntry.arguments?.getString("barcode")
            )
        }
        composable(
            route = "new/{barcode}",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) { backStackEntry -> NewBeer(viewModel = mainViewModel , barcode = backStackEntry.arguments?.getString("barcode"), onBeerSaved = { navController.navigate(Screen.Scan.route) }) }
    }
}







