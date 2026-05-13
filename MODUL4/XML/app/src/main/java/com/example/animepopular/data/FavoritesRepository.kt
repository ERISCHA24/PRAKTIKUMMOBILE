package com.example.animepopular.data

import com.example.animepopular.model.AnimeItem
import com.example.animepopular.model.Review

object FavoritesRepository {

    private val _favorites = mutableSetOf<Int>()
    private val _watched = mutableSetOf<Int>()
    private val _reviews = mutableMapOf<Int, MutableList<Review>>()

    fun isFavorite(animeId: Int): Boolean = _favorites.contains(animeId)

    fun toggleFavorite(animeId: Int) {
        if (_favorites.contains(animeId)) _favorites.remove(animeId)
        else _favorites.add(animeId)
    }

    fun getFavoriteIds(): Set<Int> = _favorites.toSet()

    fun isWatched(animeId: Int): Boolean = _watched.contains(animeId)

    fun toggleWatched(animeId: Int) {
        if (_watched.contains(animeId)) _watched.remove(animeId)
        else _watched.add(animeId)
    }

    fun getReviews(animeId: Int): List<Review> =
        _reviews[animeId]?.toList() ?: emptyList()

    fun addReview(animeId: Int, review: Review) {
        _reviews.getOrPut(animeId) { mutableListOf() }.add(0, review)
    }
}