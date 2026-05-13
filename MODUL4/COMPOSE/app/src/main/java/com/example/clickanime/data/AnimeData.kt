package com.example.clickanime.data

import com.example.clickanime.R
import com.example.clickanime.model.AnimeCharacter

object AnimeData {
    val characters = listOf(
        AnimeCharacter(
            id = 1,
            nameResId = R.string.char1_name,
            animeResId = R.string.char1_anime,
            genreResId = R.string.char1_genre,
            descriptionResId = R.string.char1_description,
            abilityResId = R.string.char1_ability,
            imageUrl = "https://cdn.myanimelist.net/images/characters/2/284121.jpg",
            myAnimeListUrl = "https://myanimelist.net/character/17/Naruto_Uzumaki",
            rating = 9.2f,
            episodesOrChapters = 720,
            year = 2002,
            typeResId = R.string.type_anime
        ),
        AnimeCharacter(
            id = 2,
            nameResId = R.string.char2_name,
            animeResId = R.string.char2_anime,
            genreResId = R.string.char2_genre,
            descriptionResId = R.string.char2_description,
            abilityResId = R.string.char2_ability,
            imageUrl = "https://cdn.myanimelist.net/images/characters/9/310307.jpg",
            myAnimeListUrl = "https://myanimelist.net/character/40/Monkey_D_Luffy",
            rating = 9.4f,
            episodesOrChapters = 1100,
            year = 1999,
            typeResId = R.string.type_anime
        ),
        AnimeCharacter(
            id = 3,
            nameResId = R.string.char3_name,
            animeResId = R.string.char3_anime,
            genreResId = R.string.char3_genre,
            descriptionResId = R.string.char3_description,
            abilityResId = R.string.char3_ability,
            imageUrl = "https://myanimelist.net/images/characters/8/349229.jpg",
            myAnimeListUrl = "https://myanimelist.net/character/5/Ichigo_Kurosaki",
            rating = 8.9f,
            episodesOrChapters = 366,
            year = 2004,
            typeResId = R.string.type_anime
        ),
        AnimeCharacter(
            id = 4,
            nameResId = R.string.char4_name,
            animeResId = R.string.char4_anime,
            genreResId = R.string.char4_genre,
            descriptionResId = R.string.char4_description,
            abilityResId = R.string.char4_ability,
            imageUrl = "https://myanimelist.net/images/characters/9/206427.jpg",
            myAnimeListUrl = "https://myanimelist.net/character/40882/Eren_Yeager",
            rating = 9.1f,
            episodesOrChapters = 87,
            year = 2013,
            typeResId = R.string.type_anime
        ),
        AnimeCharacter(
            id = 5,
            nameResId = R.string.char5_name,
            animeResId = R.string.char5_anime,
            genreResId = R.string.char5_genre,
            descriptionResId = R.string.char5_description,
            abilityResId = R.string.char5_ability,
            imageUrl = "https://myanimelist.net/images/characters/15/72546.jpg",
            myAnimeListUrl = "https://myanimelist.net/character/246/Goku",
            rating = 9.0f,
            episodesOrChapters = 291,
            year = 1989,
            typeResId = R.string.type_anime
        ),
        AnimeCharacter(
            id = 6,
            nameResId = R.string.char6_name,
            animeResId = R.string.char6_anime,
            genreResId = R.string.char6_genre,
            descriptionResId = R.string.char6_description,
            abilityResId = R.string.char6_ability,
            imageUrl = "https://myanimelist.net/images/characters/9/72533.jpg",
            myAnimeListUrl = "https://myanimelist.net/character/11/Edward_Elric",
            rating = 9.3f,
            episodesOrChapters = 64,
            year = 2009,
            typeResId = R.string.type_anime
        )
    )
}