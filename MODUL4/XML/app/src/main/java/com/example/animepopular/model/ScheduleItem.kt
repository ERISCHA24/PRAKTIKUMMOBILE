package com.example.animepopular.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleItem(
    val DateEn : String,
    val DateIn : String,
    val AninameEn : String,
    val AninameIn : String
) : Parcelable