package com.example.animepopular.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animepopular.data.AnimeRepository
import com.example.animepopular.model.AnimeItem

class AnimeViewModel : ViewModel() {

    private val _animeList = MutableLiveData<List<AnimeItem>>()
    val animeList: LiveData<List<AnimeItem>> = _animeList

    init {
        _animeList.value = AnimeRepository.getAnimeList()
    }

    fun getAnimeList(): List<AnimeItem> = AnimeRepository.getAnimeList()
}