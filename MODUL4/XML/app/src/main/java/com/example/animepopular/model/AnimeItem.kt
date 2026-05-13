package com.example.animepopular.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeItem(
    val id: Int,
    val titleEn: String,
    val titleId: String,
    val year: Int,
    val genreEn: String,
    val genreId: String,
    val plotEn: String,
    val plotId: String,
    val studioEn: String,
    val studioId: String,
    val episodes: String,
    val rating: String,
    val imageUrl: String,
    val imdbUrl: String
) : Parcelable