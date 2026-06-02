package com.example.clickanime.data.local.dao

import androidx.room.*
import com.example.clickanime.data.local.entity.FavoriteMovieEntity
import com.example.clickanime.data.local.entity.MovieDetailEntity
import com.example.clickanime.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE category = :category ORDER BY popularity DESC")
    fun getMoviesByCategory(category: String): Flow<List<MovieEntity>>

    @Query("SELECT MAX(cachedAt) FROM movies WHERE category = :category")
    suspend fun getLatestCacheTime(category: String): Long?

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :q || '%' OR originalTitle LIKE '%' || :q || '%'")
    fun searchMovies(q: String): Flow<List<MovieEntity>>

    @Query("DELETE FROM movies WHERE category = :category")
    suspend fun deleteByCategory(category: String)

    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Query("DELETE FROM movies WHERE cachedAt < :expiryTime")
    suspend fun deleteExpired(expiryTime: Long)
}

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detail: MovieDetailEntity)

    @Query("SELECT * FROM movie_details WHERE id = :movieId LIMIT 1")
    suspend fun getById(movieId: Int): MovieDetailEntity?

    @Query("SELECT cachedAt FROM movie_details WHERE id = :movieId LIMIT 1")
    suspend fun getCacheTime(movieId: Int): Long?

    @Query("DELETE FROM movie_details WHERE cachedAt < :expiryTime")
    suspend fun deleteExpired(expiryTime: Long)

    @Query("DELETE FROM movie_details")
    suspend fun clearAll()
}

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(favorite: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies WHERE movieId = :movieId")
    suspend fun removeById(movieId: Int)

    @Query("SELECT * FROM favorite_movies ORDER BY addedAt DESC")
    fun getAll(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE movieId = :movieId)")
    fun isFavorite(movieId: Int): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE movieId = :movieId)")
    suspend fun isFavoriteOnce(movieId: Int): Boolean

    @Query("SELECT COUNT(*) FROM favorite_movies")
    fun count(): Flow<Int>
}