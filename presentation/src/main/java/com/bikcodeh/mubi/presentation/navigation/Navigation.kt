package com.bikcodeh.mubi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bikcodeh.mubi.presentation.screens.home.HomeScreen
import com.bikcodeh.mubi.presentation.screens.splash.SplashScreen

@Composable
fun MubiNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navigateToHome = {
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Splash.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = Screens.Home.route) {
            HomeScreen(onClickItem = {})
        }
    }
}