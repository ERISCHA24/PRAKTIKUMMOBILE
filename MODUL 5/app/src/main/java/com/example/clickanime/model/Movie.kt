package com.example.clickanime.model

private const val IMG_BASE = "https://image.tmdb.org/t/p/"

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val genreIds: List<Int>,
    val popularity: Double,
    val originalLanguage: String,
    val category: String
) {
    val posterUrl: String?   get() = posterPath?.let   { "${IMG_BASE}w342$it" }
    val backdropUrl: String? get() = backdropPath?.let { "${IMG_BASE}w780$it" }
    val releaseYear: String  get() = releaseDate.take(4).ifEmpty { "N/A" }
    val formattedRating: String get() = String.format("%.1f", voteAverage)
}

data class MovieDetail(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val runtime: Int?,
    val genres: List<Genre>,
    val tagline: String,
    val status: String,
    val popularity: Double,
    val budget: Long,
    val revenue: Long,
    val homepage: String,
    val originalLanguage: String
) {
    val posterUrl: String?   get() = posterPath?.let   { "${IMG_BASE}w500$it" }
    val backdropUrl: String? get() = backdropPath?.let { "${IMG_BASE}w780$it" }
    val releaseYear: String  get() = releaseDate.take(4).ifEmpty { "N/A" }
    val formattedRating: String  get() = String.format("%.1f", voteAverage)
    val formattedRuntime: String get() = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "N/A"
    val genreNames: String get() = genres.joinToString(", ") { it.name }
}

data class Genre(val id: Int, val name: String)

data class FavoriteMovie(
    val movieId: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double,
    val releaseDate: String,
    val overview: String,
    val addedAt: Long
) {
    val posterUrl: String?  get() = posterPath?.let { "${IMG_BASE}w342$it" }
    val releaseYear: String get() = releaseDate.take(4).ifEmpty { "N/A" }
    val formattedRating: String get() = String.format("%.1f", voteAverage)
}