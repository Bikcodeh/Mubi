@file:OptIn(ExperimentalMaterial3Api::class)

package com.bikcodeh.mubi.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bikcodeh.mubi.presentation.screens.home.HomeScreen
import com.bikcodeh.mubi.presentation.screens.login.LoginScreen
import com.bikcodeh.mubi.presentation.screens.profile.ProfileScreen
import com.bikcodeh.mubi.presentation.screens.search.SearchScreen
import com.bikcodeh.mubi.presentation.screens.splash.SplashScreen

@ExperimentalLifecycleComposeApi
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
            }, navigateToLogin = {
                navController.navigate(Screens.Login.route) {
                    popUpTo(Screens.Splash.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = Screens.Home.route) {
            HomeScreen(
                onClickItem = {},
                navigateToProfile = {
                    navController.navigate(Screens.Profile.route)
                }, navigateToSearch = {
                    navController.navigate(Screens.Search.route)
                })
        }
        composable(route = Screens.Login.route) {
            LoginScreen(navigateToHome = {
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Login.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen(
                onLogOut = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(0)
                    }
                },
                onBack = {
                    navController.popBackStack()
                })
        }
        composable(route = Screens.Search.route) {
            SearchScreen(onBack = { navController.popBackStack() },
                onClickItem = {

                })
        }
    }
}

fun NavHostController.navigateAndClean(route: String) {
    navigate(route = route) {
        popUpTo(route = route) { inclusive = true }
    }
    graph.setStartDestination(route)
}