package com.example.clickanime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.clickanime.ui.detail.DetailScreen
import com.example.clickanime.ui.home.HomeScreen
import com.example.clickanime.ui.language.LanguageScreen
import com.example.clickanime.ui.favorites.FavoritesScreen
import com.example.clickanime.ui.search.SearchScreen
import com.example.clickanime.ui.settings.SettingsScreen
import com.example.clickanime.viewmodel.AnimeListViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: AnimeListViewModel,
    onLocaleChange: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onDetailNavigate = { characterId ->
                    viewModel.onCharacterClicked(characterId)
                    navController.navigate(Screen.Detail.createRoute(characterId))
                },
                onLanguageClick = { navController.navigate(Screen.Language.route) },
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
                onSearchClick = { navController.navigate(Screen.Search.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: return@composable
            val character = viewModel.characters.value.find { it.id == characterId }
            character?.let {
                DetailScreen(
                    character = it,
                    onBack = { navController.popBackStack() },
                    onOpenMal = { url ->
                        viewModel.onOpenMalClicked(url)
                    }
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

        composable(Screen.Favorites.route) {
            FavoritesScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Search.route) {
            SearchScreen(onBack = { navController.popBackStack() }, viewModel = viewModel)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }

        // Additional screens (About, Profile, Feedback, Help) can be added similarly.
    }
}
