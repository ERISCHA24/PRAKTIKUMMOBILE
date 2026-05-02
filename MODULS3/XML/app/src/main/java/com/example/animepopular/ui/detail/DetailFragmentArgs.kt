package com.example.animepopular.ui.detail

import android.os.Bundle
import androidx.navigation.NavArgs
import com.example.animepopular.model.AnimeItem

data class DetailFragmentArgs(val anime: AnimeItem) : NavArgs {
    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): DetailFragmentArgs {
            val anime = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("anime", AnimeItem::class.java)!!
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable("anime")!!
            }
            return DetailFragmentArgs(anime)
        }
    }
}