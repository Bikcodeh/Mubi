package com.bikcodeh.mubi.presentation.navigation

/**
 * Sealed class to wrap all the possible screens in the app
 * @param route: the specific route of the screen
 */

sealed class Screens(val route: String) {
    object Splash : Screens("splash_screen")
    object Home : Screens("home_screen")
    object Login : Screens("login_screen")
    object Profile : Screens("profile_screen")
    object Search : Screens("search_screen")
    object Detail : Screens("detail_screen/{tvShowId}/{category}/{isFavorite}") {
        const val NAV_ARG_KEY_ID = "tvShowId"
        const val NAV_ARG_KEY_CATEGORY = "category"
        const val NAV_ARG_KEY_FAVORITE = "isFavorite"
        fun passTvShowId(tvShowId: String, category: String, isFavorite: Boolean): String {
            return "detail_screen/$tvShowId/$category/$isFavorite"
        }
    }
    object Season: Screens("season_screen/{tvShowId}/{seasonNumber}") {
        const val NAV_ARG_KEY_ID = "tvShowId"
        const val NAV_ARG_SEASON_KEY_ID = "seasonNumber"
        fun passArgs(tvShowId: String, seasonNumber: Int): String {
            return "season_screen/$tvShowId/$seasonNumber"
        }
    }
}