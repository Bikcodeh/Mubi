@file:OptIn(ExperimentalMaterial3Api::class)

package com.bikcodeh.mubi.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bikcodeh.mubi.presentation.screens.detail.DetailScreen
import com.bikcodeh.mubi.presentation.screens.home.HomeScreen
import com.bikcodeh.mubi.presentation.screens.login.LoginScreen
import com.bikcodeh.mubi.presentation.screens.profile.ProfileScreen
import com.bikcodeh.mubi.presentation.screens.search.SearchScreen
import com.bikcodeh.mubi.presentation.screens.season.SeasonScreen
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
                onClickItem = {
                    navController.navigate(
                        Screens.Detail.passTvShowId(
                            tvShowId = it.id,
                            category = it.category,
                            isFavorite = it.isFavorite
                        )
                    )
                },
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
                onClickItem = {})
        }
        composable(
            route = Screens.Detail.route,
            arguments = listOf(navArgument(Screens.Detail.NAV_ARG_KEY_ID) {
                type = NavType.StringType
            }, navArgument(Screens.Detail.NAV_ARG_KEY_CATEGORY) {
                type = NavType.StringType
            }, navArgument(Screens.Detail.NAV_ARG_KEY_FAVORITE) {
                type = NavType.BoolType
            })
        ) { backStackEntry ->
            val tvShowId = backStackEntry.arguments?.getString(Screens.Detail.NAV_ARG_KEY_ID)
            val category = backStackEntry.arguments?.getString(Screens.Detail.NAV_ARG_KEY_CATEGORY)
            val isFavorite =
                backStackEntry.arguments?.getBoolean(Screens.Detail.NAV_ARG_KEY_FAVORITE, false)
            tvShowId?.let {
                DetailScreen(
                    tvShowId = tvShowId,
                    category = category ?: String(),
                    isFavorite = isFavorite!!,
                    onBack = { navController.popBackStack() },
                    onSeasonClick = { tvShowId, seasonNumber ->
                        navController.navigate(Screens.Season.passArgs(tvShowId, seasonNumber))
                    }
                )
            }
        }
        composable(
            route = Screens.Season.route,
            arguments = listOf(navArgument(Screens.Season.NAV_ARG_KEY_ID) {
                type = NavType.StringType
            }, navArgument(Screens.Season.NAV_ARG_SEASON_KEY_ID) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val tvShowId = backStackEntry.arguments?.getString(Screens.Season.NAV_ARG_KEY_ID)
            val seasonNumber =
                backStackEntry.arguments?.getInt(Screens.Season.NAV_ARG_SEASON_KEY_ID)
            tvShowId?.let {
                SeasonScreen(
                    onBack = { navController.popBackStack() },
                    tvShowId = tvShowId,
                    seasonNumber = seasonNumber ?: -1
                )
            }
        }
    }
}