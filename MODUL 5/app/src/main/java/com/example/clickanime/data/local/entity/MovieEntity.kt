package com.example.clickanime.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val genreIds: String,
    val popularity: Double,
    val originalLanguage: String,
    val category: String,
    val cachedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "movie_details")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val runtime: Int?,
    val genres: String,
    val tagline: String,
    val status: String,
    val popularity: Double,
    val budget: Long,
    val revenue: Long,
    val homepage: String,
    val originalLanguage: String,
    val cachedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val movieId: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double,
    val releaseDate: String,
    val overview: String,
    val addedAt: Long = System.currentTimeMillis()
)