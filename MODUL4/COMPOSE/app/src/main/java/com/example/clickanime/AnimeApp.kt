package com.example.clickanime

import android.app.Application
import timber.log.Timber

class AnimeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
