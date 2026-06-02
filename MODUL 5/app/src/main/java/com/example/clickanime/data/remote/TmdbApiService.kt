package com.example.clickanime.data.remote

import com.example.clickanime.data.remote.model.TmdbGenreListResponse
import com.example.clickanime.data.remote.model.TmdbMovieDetailDto
import com.example.clickanime.data.remote.model.TmdbMovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page")     page: Int = 1
    ): Response<TmdbMovieListResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page")     page: Int = 1
    ): Response<TmdbMovieListResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page")     page: Int = 1
    ): Response<TmdbMovieListResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "en-US",
        @Query("page")     page: Int = 1
    ): Response<TmdbMovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id")  movieId: Int,
        @Query("language") language: String = "en-US"
    ): Response<TmdbMovieDetailDto>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query")    query: String,
        @Query("language") language: String = "en-US",
        @Query("page")     page: Int = 1
    ): Response<TmdbMovieListResponse>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") language: String = "en-US"
    ): Response<TmdbGenreListResponse>
}