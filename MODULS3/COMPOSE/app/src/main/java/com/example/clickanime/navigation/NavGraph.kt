package com.example.clickanime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.clickanime.data.AnimeData
import com.example.clickanime.ui.detail.DetailScreen
import com.example.clickanime.ui.home.HomeScreen
import com.example.clickanime.ui.language.LanguageScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    onLocaleChange: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                characters = AnimeData.characters,
                onDetailClick = { characterId ->
                    navController.navigate(Screen.Detail.createRoute(characterId))
                },
                onLanguageClick = {
                    navController.navigate(Screen.Language.route)
                }
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: return@composable
            val character = AnimeData.characters.find { it.id == characterId }
            character?.let {
                DetailScreen(
                    character = it,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(Screen.Language.route) {
            LanguageScreen(
                onLanguageSelected = { langCode ->
                    onLocaleChange(langCode)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}