package com.bikcodeh.mubi.presentation.navigation

sealed class Screens(val route: String) {
    object Splash: Screens("splash_screen")
    object Home: Screens("home_screen")
    object Login: Screens("login_screen")
    object Profile: Screens("profile_screen")
}