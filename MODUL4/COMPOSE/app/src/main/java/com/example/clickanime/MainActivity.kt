package com.example.clickanime

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.clickanime.navigation.NavGraph
import com.example.clickanime.ui.theme.AnimeAppTheme
import com.example.clickanime.viewmodel.AnimeListViewModel
import com.example.clickanime.viewmodel.AnimeListViewModelFactory
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lang = prefs.getString("language", Locale.getDefault().language) ?: "en"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = newBase.resources.configuration
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val factory = remember { AnimeListViewModelFactory("AnimeList") }
                    val animeListViewModel: AnimeListViewModel = viewModel(factory = factory)
                    NavGraph(
                        navController = navController,
                        viewModel = animeListViewModel,
                        onLocaleChange = { langCode -> applyLocale(langCode) }
                    )
                }
            }
        }
    }

    private fun applyLocale(langCode: String) {
        getSharedPreferences("settings", Context.MODE_PRIVATE)
            .edit()
            .putString("language", langCode)
            .apply()
        recreate()
    }
}
