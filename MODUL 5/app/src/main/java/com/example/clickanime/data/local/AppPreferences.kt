package com.example.clickanime.data.local

import android.content.Context

class AppPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var language: String
        get() = prefs.getString(KEY_LANGUAGE, "en") ?: "en"
        set(v) = prefs.edit().putString(KEY_LANGUAGE, v).apply()

    var isNotificationsEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATIONS, false)
        set(v) = prefs.edit().putBoolean(KEY_NOTIFICATIONS, v).apply()

    var isGridView: Boolean
        get() = prefs.getBoolean(KEY_GRID_VIEW, false)
        set(v) = prefs.edit().putBoolean(KEY_GRID_VIEW, v).apply()

    var lastMovieCategory: String
        get() = prefs.getString(KEY_LAST_CATEGORY, CAT_POPULAR) ?: CAT_POPULAR
        set(v) = prefs.edit().putString(KEY_LAST_CATEGORY, v).apply()

    companion object {
        const val PREFS_NAME   = "clickanime_settings"
        private const val KEY_LANGUAGE       = "language"
        private const val KEY_NOTIFICATIONS  = "notifications_enabled"
        private const val KEY_GRID_VIEW      = "grid_view"
        private const val KEY_LAST_CATEGORY  = "last_movie_category"

        const val CAT_POPULAR     = "popular"
        const val CAT_NOW_PLAYING = "now_playing"
        const val CAT_TOP_RATED   = "top_rated"
        const val CAT_UPCOMING    = "upcoming"
    }
}