package com.example.clickanime.data.mapper

import com.example.clickanime.data.local.entity.FavoriteMovieEntity
import com.example.clickanime.data.local.entity.MovieDetailEntity
import com.example.clickanime.data.local.entity.MovieEntity
import com.example.clickanime.data.remote.model.TmdbGenreDto
import com.example.clickanime.data.remote.model.TmdbMovieDetailDto
import com.example.clickanime.data.remote.model.TmdbMovieDto
import com.example.clickanime.model.FavoriteMovie
import com.example.clickanime.model.Genre
import com.example.clickanime.model.Movie
import com.example.clickanime.model.MovieDetail

fun TmdbMovieDto.toEntity(category: String) = MovieEntity(
    id = id, title = title, originalTitle = originalTitle,
    overview = overview, posterPath = posterPath, backdropPath = backdropPath,
    voteAverage = voteAverage, voteCount = voteCount, releaseDate = releaseDate,
    genreIds = genreIds.joinToString(","), popularity = popularity,
    originalLanguage = originalLanguage, category = category,
    cachedAt = System.currentTimeMillis()
)

fun TmdbMovieDetailDto.toEntity() = MovieDetailEntity(
    id = id, title = title, originalTitle = originalTitle,
    overview = overview, posterPath = posterPath, backdropPath = backdropPath,
    voteAverage = voteAverage, voteCount = voteCount, releaseDate = releaseDate,
    runtime = runtime, genres = genres.joinToString(",") { "${it.id}:${it.name}" },
    tagline = tagline, status = status, popularity = popularity,
    budget = budget, revenue = revenue, homepage = homepage,
    originalLanguage = originalLanguage, cachedAt = System.currentTimeMillis()
)

fun TmdbMovieDto.toDomain(category: String) = Movie(
    id = id, title = title, originalTitle = originalTitle,
    overview = overview, posterPath = posterPath, backdropPath = backdropPath,
    voteAverage = voteAverage, voteCount = voteCount, releaseDate = releaseDate,
    genreIds = genreIds, popularity = popularity,
    originalLanguage = originalLanguage, category = category
)

fun TmdbMovieDetailDto.toDomain() = MovieDetail(
    id = id, title = title, originalTitle = originalTitle,
    overview = overview, posterPath = posterPath, backdropPath = backdropPath,
    voteAverage = voteAverage, voteCount = voteCount, releaseDate = releaseDate,
    runtime = runtime, genres = genres.map { Genre(it.id, it.name) },
    tagline = tagline, status = status, popularity = popularity,
    budget = budget, revenue = revenue, homepage = homepage,
    originalLanguage = originalLanguage
)

fun MovieEntity.toDomain() = Movie(
    id = id, title = title, originalTitle = originalTitle,
    overview = overview, posterPath = posterPath, backdropPath = backdropPath,
    voteAverage = voteAverage, voteCount = voteCount, releaseDate = releaseDate,
    genreIds = genreIds.split(",").mapNotNull { it.trim().toIntOrNull() },
    popularity = popularity, originalLanguage = originalLanguage, category = category
)

fun MovieDetailEntity.toDomain() = MovieDetail(
    id = id, title = title, originalTitle = originalTitle,
    overview = overview, posterPath = posterPath, backdropPath = backdropPath,
    voteAverage = voteAverage, voteCount = voteCount, releaseDate = releaseDate,
    runtime = runtime, genres = parseGenres(genres),
    tagline = tagline, status = status, popularity = popularity,
    budget = budget, revenue = revenue, homepage = homepage,
    originalLanguage = originalLanguage
)

fun FavoriteMovieEntity.toDomain() = FavoriteMovie(
    movieId = movieId, title = title, posterPath = posterPath,
    voteAverage = voteAverage, releaseDate = releaseDate,
    overview = overview, addedAt = addedAt
)

fun Movie.toFavoriteEntity() = FavoriteMovieEntity(
    movieId = id, title = title, posterPath = posterPath,
    voteAverage = voteAverage, releaseDate = releaseDate,
    overview = overview, addedAt = System.currentTimeMillis()
)

fun MovieDetail.toFavoriteEntity() = FavoriteMovieEntity(
    movieId = id, title = title, posterPath = posterPath,
    voteAverage = voteAverage, releaseDate = releaseDate,
    overview = overview, addedAt = System.currentTimeMillis()
)

private fun parseGenres(raw: String): List<Genre> {
    if (raw.isBlank()) return emptyList()
    return raw.split(",").mapNotNull { part ->
        val s = part.trim().split(":")
        if (s.size == 2) Genre(s[0].toIntOrNull() ?: return@mapNotNull null, s[1]) else null
    }
}