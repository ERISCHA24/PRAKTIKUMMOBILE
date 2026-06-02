package com.example.clickanime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.clickanime.data.local.AppPreferences
import com.example.clickanime.data.remote.model.ApiResponse
import com.example.clickanime.data.repository.MovieRepository
import com.example.clickanime.model.FavoriteMovie
import com.example.clickanime.model.Movie
import com.example.clickanime.model.MovieDetail
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(
    private val repo: MovieRepository,
    private val prefs: AppPreferences
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(
        prefs.lastMovieCategory.ifEmpty { MovieRepository.CAT_POPULAR }
    )
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _listState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val listState: StateFlow<MovieListUiState> = _listState.asStateFlow()

    private val _detailState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Idle)
    val detailState: StateFlow<MovieDetailUiState> = _detailState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchState = MutableStateFlow<MovieSearchUiState>(MovieSearchUiState.Idle)
    val searchState: StateFlow<MovieSearchUiState> = _searchState.asStateFlow()

    val favorites: StateFlow<List<FavoriteMovie>> = repo.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val favoriteCount: StateFlow<Int> = repo.getFavoriteCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    private var listJob: Job? = null
    private var searchJob: Job? = null

    init {
        loadMovies(_selectedCategory.value)
        viewModelScope.launch { repo.clearExpiredCache() }
    }

    fun selectCategory(cat: String) {
        if (_selectedCategory.value == cat) return
        _selectedCategory.value = cat
        prefs.lastMovieCategory = cat   // Simpan ke SharedPreferences
        loadMovies(cat)
        Timber.d("Category → $cat")
    }

    fun loadMovies(category: String = _selectedCategory.value, forceRefresh: Boolean = false) {
        listJob?.cancel()
        listJob = viewModelScope.launch {
            _listState.value = MovieListUiState.Loading
            repo.getMovies(category, forceRefresh)
                .catch { e ->
                    _listState.value = MovieListUiState.Error(e.message ?: "Unknown error")
                }
                .collect { response ->
                    _listState.value = when (response) {
                        is ApiResponse.Success -> {
                            if (response.data.isEmpty()) MovieListUiState.Loading
                            else MovieListUiState.Success(response.data)
                        }
                        is ApiResponse.Error     -> MovieListUiState.Error("Error ${response.code}: ${response.message}")
                        is ApiResponse.Exception -> MovieListUiState.Error(response.e.message ?: "Network error")
                    }
                }
        }
    }

    fun refresh() = loadMovies(forceRefresh = true)

    fun loadDetail(movieId: Int) {
        _detailState.value = MovieDetailUiState.Loading
        viewModelScope.launch {
            _detailState.value = when (val r = repo.getMovieDetail(movieId)) {
                is ApiResponse.Success   -> MovieDetailUiState.Success(r.data)
                is ApiResponse.Error     -> MovieDetailUiState.Error("Error ${r.code}: ${r.message}")
                is ApiResponse.Exception -> MovieDetailUiState.Error(r.e.message ?: "Failed to load")
            }
        }
    }

    fun clearDetail() { _detailState.value = MovieDetailUiState.Idle }

    fun updateSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        if (query.isBlank()) { _searchState.value = MovieSearchUiState.Idle; return }
        searchJob = viewModelScope.launch {
            _searchState.value = MovieSearchUiState.Loading
            delay(500)   // debounce
            _searchState.value = when (val r = repo.searchMovies(query)) {
                is ApiResponse.Success   -> if (r.data.isEmpty()) MovieSearchUiState.Empty
                else MovieSearchUiState.Success(r.data)
                is ApiResponse.Error     -> MovieSearchUiState.Error("Error ${r.code}: ${r.message}")
                is ApiResponse.Exception -> MovieSearchUiState.Error(r.e.message ?: "Search failed")
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchState.value = MovieSearchUiState.Idle
        searchJob?.cancel()
    }

    fun isFavoriteFlow(movieId: Int): Flow<Boolean> = repo.isFavorite(movieId)

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            val isFav = repo.isFavorite(movie.id).first()
            if (isFav) repo.removeFavorite(movie.id) else repo.addFavorite(movie)
        }
    }

    fun toggleFavorite(detail: MovieDetail) {
        viewModelScope.launch {
            val isFav = repo.isFavorite(detail.id).first()
            if (isFav) repo.removeFavorite(detail.id) else repo.addFavorite(detail)
        }
    }

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch { repo.removeFavorite(movieId) }
    }

    fun clearAllCache() {
        viewModelScope.launch {
            repo.clearAllCache()
            loadMovies(forceRefresh = true)
        }
    }
}

class MovieViewModelFactory(
    private val repo: MovieRepository,
    private val prefs: AppPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repo, prefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
    }
}