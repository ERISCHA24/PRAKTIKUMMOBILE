package com.example.clickanime.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.clickanime.data.local.dao.FavoriteMovieDao
import com.example.clickanime.data.local.dao.MovieDao
import com.example.clickanime.data.local.dao.MovieDetailDao
import com.example.clickanime.data.local.entity.FavoriteMovieEntity
import com.example.clickanime.data.local.entity.MovieDetailEntity
import com.example.clickanime.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailEntity::class, FavoriteMovieEntity::class],
    version  = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieDetailDao(): MovieDetailDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        @Volatile private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "clickanime_movie_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}