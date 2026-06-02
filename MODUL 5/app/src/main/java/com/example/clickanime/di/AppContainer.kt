package com.example.clickanime.di

import android.content.Context
import com.example.clickanime.data.local.AppPreferences
import com.example.clickanime.data.local.MovieDatabase
import com.example.clickanime.data.remote.NetworkClient
import com.example.clickanime.data.repository.MovieRepository
import com.example.clickanime.viewmodel.MovieViewModelFactory

class AppContainer(context: Context) {

    val appPreferences = AppPreferences(context)

    private val db = MovieDatabase.getInstance(context)

    private val repo = MovieRepository(
        api        = NetworkClient.tmdbApiService,
        movieDao   = db.movieDao(),
        detailDao  = db.movieDetailDao(),
        favDao     = db.favoriteMovieDao(),
        prefs      = appPreferences
    )

    val movieViewModelFactory = MovieViewModelFactory(repo, appPreferences)
}