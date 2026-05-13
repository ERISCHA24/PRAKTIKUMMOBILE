package com.example.animepopular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnimeViewModelFactory(private val filterParam: String = "all") : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeViewModel(filterParam) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
