package com.example.clickanime.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clickanime.data.AnimeData
import com.example.clickanime.model.AnimeCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimeViewModel : ViewModel() {

    private val _characters = MutableStateFlow<List<AnimeCharacter>>(AnimeData.characters)
    val characters: StateFlow<List<AnimeCharacter>> = _characters.asStateFlow()

    private val _carouselIndex = MutableStateFlow(0)
    val carouselIndex: StateFlow<Int> = _carouselIndex.asStateFlow()

    fun updateCarouselIndex(index: Int) {
        _carouselIndex.value = index
    }
}