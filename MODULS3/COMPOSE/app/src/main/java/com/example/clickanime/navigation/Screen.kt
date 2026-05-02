package com.example.clickanime.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{characterId}") {
        fun createRoute(characterId: Int) = "detail/$characterId"
    }
    object Language : Screen("language")
}