package com.bikcodeh.mubi.presentation.navigation

sealed class Screens(val route: String) {
    object Splash : Screens("splash_screen")
    object Home : Screens("home_screen")
    object Login : Screens("login_screen")
    object Profile : Screens("profile_screen")
    object Search : Screens("search_screen")
    object Detail : Screens("detail_screen/{tvShowId}") {
        const val NAV_ARG_KEY = "tvShowId"
        fun passTvShowId(tvShowId: String): String {
            return "detail_screen/${tvShowId}"
        }
    }
}