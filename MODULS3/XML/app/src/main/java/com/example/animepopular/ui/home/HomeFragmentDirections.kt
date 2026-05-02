package com.example.animepopular.ui.home

import androidx.navigation.NavDirections
import com.example.animepopular.R
import com.example.animepopular.model.AnimeItem
import android.os.Bundle

class HomeFragmentDirections private constructor() {
    companion object {
        fun actionHomeFragmentToDetailFragment(anime: AnimeItem): NavDirections =
            ActionHomeToDetail(anime)

        fun actionHomeFragmentToLanguageFragment(): NavDirections =
            object : NavDirections {
                override val actionId: Int = R.id.action_homeFragment_to_languageFragment
                override val arguments: Bundle = Bundle()
            }
    }

    private class ActionHomeToDetail(private val anime: AnimeItem) : NavDirections {
        override val actionId: Int = R.id.action_homeFragment_to_detailFragment
        override val arguments: Bundle
            get() = Bundle().also { it.putParcelable("anime", anime) }
    }
}