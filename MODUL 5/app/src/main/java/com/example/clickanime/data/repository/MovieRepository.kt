package com.example.clickanime.data.repository

import com.example.clickanime.data.local.AppPreferences
import com.example.clickanime.data.local.dao.FavoriteMovieDao
import com.example.clickanime.data.local.dao.MovieDao
import com.example.clickanime.data.local.dao.MovieDetailDao
import com.example.clickanime.data.mapper.toDomain
import com.example.clickanime.data.mapper.toEntity
import com.example.clickanime.data.mapper.toFavoriteEntity
import com.example.clickanime.data.remote.TmdbApiService
import com.example.clickanime.data.remote.model.ApiResponse
import com.example.clickanime.data.remote.safeApiCall
import com.example.clickanime.model.FavoriteMovie
import com.example.clickanime.model.Movie
import com.example.clickanime.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class MovieRepository(
    private val api: TmdbApiService,
    private val movieDao: MovieDao,
    private val detailDao: MovieDetailDao,
    private val favDao: FavoriteMovieDao,
    private val prefs: AppPreferences
) {
    companion object {
        private const val TTL_MS = 30 * 60 * 1000L

        const val CAT_POPULAR     = "popular"
        const val CAT_NOW_PLAYING = "now_playing"
        const val CAT_TOP_RATED   = "top_rated"
        const val CAT_UPCOMING    = "upcoming"
    }

    fun getMovies(
        category: String,
        forceRefresh: Boolean = false
    ): Flow<ApiResponse<List<Movie>>> = flow {

        val lastCache    = movieDao.getLatestCacheTime(category) ?: 0L
        val cacheValid   = (System.currentTimeMillis() - lastCache) < TTL_MS

        if (!cacheValid || forceRefresh) {
            Timber.d("Fetching $category from API (forceRefresh=$forceRefresh)")
            val result = fetchAndCache(category)
            if (result is ApiResponse.Error || result is ApiResponse.Exception) {
                if (lastCache == 0L) {
                    @Suppress("UNCHECKED_CAST")
                    emit(result as ApiResponse<List<Movie>>)
                    return@flow
                }
                Timber.w("API failed for $category, using stale cache")
            }
        } else {
            Timber.d("Using valid cache for $category")
        }

        movieDao.getMoviesByCategory(category).collect { entities ->
            emit(ApiResponse.Success(entities.map { it.toDomain() }))
        }
    }

    private suspend fun fetchAndCache(category: String): ApiResponse<List<Movie>> {
        val response = safeApiCall {
            when (category) {
                CAT_POPULAR     -> api.getPopularMovies()
                CAT_NOW_PLAYING -> api.getNowPlayingMovies()
                CAT_TOP_RATED   -> api.getTopRatedMovies()
                CAT_UPCOMING    -> api.getUpcomingMovies()
                else            -> api.getPopularMovies()
            }
        }
        return when (response) {
            is ApiResponse.Success -> {
                val entities = response.data.results.map { it.toEntity(category) }
                movieDao.deleteByCategory(category)
                movieDao.insertMovies(entities)
                Timber.d("Cached ${entities.size} movies [$category]")
                ApiResponse.Success(entities.map { it.toDomain() })
            }
            else -> response as ApiResponse<List<Movie>>
        }
    }

    suspend fun getMovieDetail(movieId: Int, forceRefresh: Boolean = false): ApiResponse<MovieDetail> {
        val lastCache  = detailDao.getCacheTime(movieId) ?: 0L
        val cacheValid = (System.currentTimeMillis() - lastCache) < TTL_MS

        if (cacheValid && !forceRefresh) {
            val cached = detailDao.getById(movieId)
            if (cached != null) {
                Timber.d("Using cached detail for movieId=$movieId")
                return ApiResponse.Success(cached.toDomain())
            }
        }

        Timber.d("Fetching detail from API movieId=$movieId")
        return when (val r = safeApiCall { api.getMovieDetail(movieId) }) {
            is ApiResponse.Success -> {
                detailDao.insert(r.data.toEntity())
                ApiResponse.Success(r.data.toDomain())
            }
            else -> {
                // Fallback ke stale cache jika ada
                val stale = detailDao.getById(movieId)
                if (stale != null) ApiResponse.Success(stale.toDomain())
                else r as ApiResponse<MovieDetail>
            }
        }
    }

    suspend fun searchMovies(query: String): ApiResponse<List<Movie>> {
        if (query.isBlank()) return ApiResponse.Success(emptyList())
        return when (val r = safeApiCall { api.searchMovies(query) }) {
            is ApiResponse.Success -> ApiResponse.Success(
                r.data.results.map { it.toDomain(CAT_POPULAR) }
            )
            else -> r as ApiResponse<List<Movie>>
        }
    }

    fun getAllFavorites(): Flow<List<FavoriteMovie>> =
        favDao.getAll().map { it.map { e -> e.toDomain() } }

    fun isFavorite(movieId: Int): Flow<Boolean> = favDao.isFavorite(movieId)

    fun getFavoriteCount(): Flow<Int> = favDao.count()

    suspend fun addFavorite(movie: Movie)       { favDao.add(movie.toFavoriteEntity()) }
    suspend fun addFavorite(detail: MovieDetail){ favDao.add(detail.toFavoriteEntity()) }
    suspend fun removeFavorite(movieId: Int)    { favDao.removeById(movieId) }

    suspend fun clearExpiredCache() {
        val exp = System.currentTimeMillis() - TTL_MS
        movieDao.deleteExpired(exp)
        detailDao.deleteExpired(exp)
        Timber.d("Expired cache cleared")
    }

    suspend fun clearAllCache() {
        movieDao.clearAll()
        detailDao.clearAll()
        Timber.d("All cache cleared")
    }

    var lastCategory: String
        get() = prefs.lastMovieCategory
        set(v) { prefs.lastMovieCategory = v }
}