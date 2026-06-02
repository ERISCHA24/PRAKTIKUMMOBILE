package com.example.clickanime.viewmodel

import com.example.clickanime.model.Movie
import com.example.clickanime.model.MovieDetail

sealed class MovieListUiState {
    object Loading : MovieListUiState()
    data class Success(val movies: List<Movie>) : MovieListUiState()
    data class Error(val message: String) : MovieListUiState()
}

sealed class MovieDetailUiState {
    object Idle : MovieDetailUiState()
    object Loading : MovieDetailUiState()
    data class Success(val movie: MovieDetail) : MovieDetailUiState()
    data class Error(val message: String) : MovieDetailUiState()
}

sealed class MovieSearchUiState {
    object Idle : MovieSearchUiState()
    object Loading : MovieSearchUiState()
    data class Success(val movies: List<Movie>) : MovieSearchUiState()
    data class Error(val message: String) : MovieSearchUiState()
    object Empty : MovieSearchUiState()
}