@file:OptIn(ExperimentalMaterial3Api::class)

package com.bikcodeh.mubi.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.bikcodeh.mubi.presentation.screens.detail.DetailScreen
import com.bikcodeh.mubi.presentation.screens.home.HomeScreen
import com.bikcodeh.mubi.presentation.screens.login.LoginScreen
import com.bikcodeh.mubi.presentation.screens.profile.ProfileScreen
import com.bikcodeh.mubi.presentation.screens.search.SearchScreen
import com.bikcodeh.mubi.presentation.screens.season.SeasonScreen
import com.bikcodeh.mubi.presentation.screens.splash.SplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@ExperimentalLifecycleComposeApi
@Composable
fun MubiNavigation(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(route = Screens.Splash.route,
            enterTransition = {
                slideInVertically()
            }, exitTransition = {
                slideOutVertically()
            }) {
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
        composable(route = Screens.Home.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
            }
        ) {
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
        composable(route = Screens.Login.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
            }) {
            LoginScreen(navigateToHome = {
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Login.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = Screens.Profile.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
            }) {
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
        composable(route = Screens.Search.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
            }) {
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
            }),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
            }
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
            }),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
            }
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