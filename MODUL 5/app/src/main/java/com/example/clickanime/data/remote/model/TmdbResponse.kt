package com.example.clickanime.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val code: Int, val message: String) : ApiResponse<Nothing>()
    data class Exception(val e: Throwable) : ApiResponse<Nothing>()
}
@Serializable
data class TmdbMovieListResponse(
    @SerialName("page")          val page: Int = 1,
    @SerialName("results")       val results: List<TmdbMovieDto> = emptyList(),
    @SerialName("total_pages")   val totalPages: Int = 1,
    @SerialName("total_results") val totalResults: Int = 0
)

@Serializable
data class TmdbMovieDto(
    @SerialName("id")                val id: Int,
    @SerialName("title")             val title: String = "",
    @SerialName("original_title")    val originalTitle: String = "",
    @SerialName("overview")          val overview: String = "",
    @SerialName("poster_path")       val posterPath: String? = null,
    @SerialName("backdrop_path")     val backdropPath: String? = null,
    @SerialName("vote_average")      val voteAverage: Double = 0.0,
    @SerialName("vote_count")        val voteCount: Int = 0,
    @SerialName("release_date")      val releaseDate: String = "",
    @SerialName("genre_ids")         val genreIds: List<Int> = emptyList(),
    @SerialName("popularity")        val popularity: Double = 0.0,
    @SerialName("adult")             val adult: Boolean = false,
    @SerialName("original_language") val originalLanguage: String = ""
)

@Serializable
data class TmdbMovieDetailDto(
    @SerialName("id")                val id: Int,
    @SerialName("title")             val title: String = "",
    @SerialName("original_title")    val originalTitle: String = "",
    @SerialName("overview")          val overview: String = "",
    @SerialName("poster_path")       val posterPath: String? = null,
    @SerialName("backdrop_path")     val backdropPath: String? = null,
    @SerialName("vote_average")      val voteAverage: Double = 0.0,
    @SerialName("vote_count")        val voteCount: Int = 0,
    @SerialName("release_date")      val releaseDate: String = "",
    @SerialName("runtime")           val runtime: Int? = null,
    @SerialName("genres")            val genres: List<TmdbGenreDto> = emptyList(),
    @SerialName("tagline")           val tagline: String = "",
    @SerialName("status")            val status: String = "",
    @SerialName("popularity")        val popularity: Double = 0.0,
    @SerialName("budget")            val budget: Long = 0L,
    @SerialName("revenue")           val revenue: Long = 0L,
    @SerialName("homepage")          val homepage: String = "",
    @SerialName("original_language") val originalLanguage: String = ""
)

@Serializable
data class TmdbGenreDto(
    @SerialName("id")   val id: Int,
    @SerialName("name") val name: String
)

@Serializable
data class TmdbGenreListResponse(
    @SerialName("genres") val genres: List<TmdbGenreDto> = emptyList()
)