package com.example.clickanime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickanime.data.AnimeData
import com.example.clickanime.model.AnimeCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class AnimeListViewModel(private val logTag: String) : ViewModel() {

    // StateFlow for the list of characters
    private val _characters = MutableStateFlow<List<AnimeCharacter>>(emptyList())
    val characters: StateFlow<List<AnimeCharacter>> = _characters.asStateFlow()

    // StateFlow for selected character id (event-like)
    private val _selectedCharacterId = MutableStateFlow<Int?>(null)
    val selectedCharacterId: StateFlow<Int?> = _selectedCharacterId.asStateFlow()

    // StateFlow for explicit intent (open external URL)
    private val _openMalUrl = MutableStateFlow<String?>(null)
    val openMalUrl: StateFlow<String?> = _openMalUrl.asStateFlow()

    init {
        val list = AnimeData.characters
        _characters.value = list
        Timber.tag(logTag).i("Loaded %d characters into list", list.size)
    }

    fun onCharacterClicked(id: Int) {
        _selectedCharacterId.value = id
        val character = _characters.value.find { it.id == id }
        character?.let {
            Timber.tag(logTag).i(
                "Character selected: id=%d nameResId=%d animeResId=%d",
                it.id,
                it.nameResId,
                it.animeResId
            )
        } ?: Timber.tag(logTag).w("Character clicked but not found: id=%d", id)
    }

    fun clearSelectedCharacter() {
        _selectedCharacterId.value = null
    }

    fun onOpenMalClicked(url: String) {
        _openMalUrl.value = url
        Timber.tag(logTag).i("Open MAL pressed for url=%s", url)
    }

    fun clearOpenMalEvent() {
        _openMalUrl.value = null
    }
}

class AnimeListViewModelFactory(private val logTag: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeListViewModel(logTag) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
