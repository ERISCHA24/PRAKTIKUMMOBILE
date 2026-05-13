package com.example.animepopular.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    val NewsEn : String,
    val NewsIn : String,
    val ContentEn : String,
    val ContentIn : String,
    val UpdateEn : String,
    val UpdateIn : String
) : Parcelable