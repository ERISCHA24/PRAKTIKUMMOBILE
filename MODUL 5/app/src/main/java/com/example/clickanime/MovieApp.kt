package com.example.clickanime

import android.app.Application
import com.example.clickanime.di.AppContainer
import timber.log.Timber

class MovieApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("MovieApp initialized")
    }
}