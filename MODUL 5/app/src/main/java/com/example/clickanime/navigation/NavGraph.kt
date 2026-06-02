package com.example.clickanime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.clickanime.data.local.AppPreferences
import com.example.clickanime.ui.detail.DetailScreen
import com.example.clickanime.ui.favorites.FavoritesScreen
import com.example.clickanime.ui.home.HomeScreen
import com.example.clickanime.ui.language.LanguageScreen
import com.example.clickanime.ui.search.SearchScreen
import com.example.clickanime.ui.settings.SettingsScreen
import com.example.clickanime.viewmodel.MovieViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    movieViewModel: MovieViewModel,
    appPreferences: AppPreferences,
    onLocaleChange: (String) -> Unit
) {
    NavHost(
        navController  = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel      = movieViewModel,
                onMovieClick   = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                onSearchClick  = { navController.navigate(Screen.Search.route) },
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
                onLanguageClick = { navController.navigate(Screen.Language.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(
            route     = Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { back ->
            val movieId = back.arguments?.getInt("movieId") ?: return@composable
            DetailScreen(
                movieId   = movieId,
                viewModel = movieViewModel,
                onBack    = { navController.popBackStack() }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                viewModel    = movieViewModel,
                onMovieClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                onBack       = { navController.popBackStack() }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel    = movieViewModel,
                onMovieClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                onBack       = { navController.popBackStack() }
            )
        }

        composable(Screen.Language.route) {
            LanguageScreen(
                onLanguageSelected = { lang ->
                    onLocaleChange(lang)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel    = movieViewModel,
                preferences  = appPreferences,
                onBack       = { navController.popBackStack() }
            )
        }
    }
}