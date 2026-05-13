package com.example.animepopular.viewmodel

import androidx.lifecycle.ViewModel
import com.example.animepopular.data.AnimeRepository
import com.example.animepopular.model.AnimeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class AnimeViewModel(private val filterParam: String = "all") : ViewModel() {

    private val _animeList = MutableStateFlow<List<AnimeItem>>(emptyList())
    val animeList: StateFlow<List<AnimeItem>> = _animeList

    private val _selectedAnime = MutableStateFlow<AnimeItem?>(null)
    val selectedAnime: StateFlow<AnimeItem?> = _selectedAnime

    private val _detailButtonClicked = MutableStateFlow(false)
    val detailButtonClicked: StateFlow<Boolean> = _detailButtonClicked

    private val _explicitIntentClicked = MutableStateFlow(false)
    val explicitIntentClicked: StateFlow<Boolean> = _explicitIntentClicked

    init {
        loadAnimeList()
    }

    private fun loadAnimeList() {
        val list = AnimeRepository.getAnimeList()
        _animeList.value = list
        Timber.d("Anime list loaded with ${list.size} items")
    }

    fun onDetailButtonClicked(anime: AnimeItem) {
        Timber.d("Detail button clicked for anime: ${anime.titleEn} (ID: ${anime.id})")
        _selectedAnime.value = anime
        _detailButtonClicked.value = true
        Timber.d("Selected anime data - Title: ${anime.titleEn}, Year: ${anime.year}, Rating: ${anime.rating}")
    }

    fun onExplicitIntentClicked(anime: AnimeItem) {
        Timber.d("Explicit Intent button clicked for anime: ${anime.titleEn} (ID: ${anime.id})")
        _selectedAnime.value = anime
        _explicitIntentClicked.value = true
    }

    fun resetDetailButtonState() {
        _detailButtonClicked.value = false
    }

    fun resetExplicitIntentState() {
        _explicitIntentClicked.value = false
    }

    fun addAnimeToList(anime: AnimeItem) {
        Timber.d("Adding anime to list: ${anime.titleEn} (ID: ${anime.id})")
        val currentList = _animeList.value.toMutableList()
        if (!currentList.any { it.id == anime.id }) {
            currentList.add(anime)
            _animeList.value = currentList
            Timber.d("Anime added successfully. Total items: ${currentList.size}")
        } else {
            Timber.w("Anime already exists in list: ${anime.titleEn}")
        }
    }

    fun getFilterParam(): String {
        Timber.d("Filter parameter: $filterParam")
        return filterParam
    }
}
