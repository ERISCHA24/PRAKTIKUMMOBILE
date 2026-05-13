package com.example.clickanime.model

data class AnimeCharacter(
    val id: Int,
    val nameResId: Int,
    val animeResId: Int,
    val genreResId: Int,
    val descriptionResId: Int,
    val abilityResId: Int,
    val imageUrl: String,
    val myAnimeListUrl: String,
    val rating: Float,
    val episodesOrChapters: Int,
    val year: Int,
    val typeResId: Int
)