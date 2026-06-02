package com.example.clickanime.navigation

sealed class Screen(val route: String) {
    object Home        : Screen("home")
    object Detail      : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
    object Search      : Screen("search")
    object Favorites   : Screen("favorites")
    object Settings    : Screen("settings")
    object Language    : Screen("language")
}