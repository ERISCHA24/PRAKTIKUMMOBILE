package com.example.clickanime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.clickanime.R
import com.example.clickanime.data.repository.MovieRepository
import com.example.clickanime.model.Movie
import com.example.clickanime.viewmodel.MovieListUiState
import com.example.clickanime.viewmodel.MovieViewModel

private data class CategoryTab(val key: String, val labelRes: Int)

private val CATEGORIES = listOf(
    CategoryTab(MovieRepository.CAT_POPULAR,     R.string.category_popular),
    CategoryTab(MovieRepository.CAT_NOW_PLAYING, R.string.category_now_playing),
    CategoryTab(MovieRepository.CAT_TOP_RATED,   R.string.category_top_rated),
    CategoryTab(MovieRepository.CAT_UPCOMING,    R.string.category_upcoming),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MovieViewModel,
    onMovieClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onLanguageClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val listState        by viewModel.listState.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val favCount         by viewModel.favoriteCount.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_title), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Default.Search, contentDescription = stringResource(R.string.nav_search))
                    }
                    BadgedBox(badge = {
                        if (favCount > 0) Badge { Text(favCount.toString()) }
                    }) {
                        IconButton(onClick = onFavoritesClick) {
                            Icon(Icons.Default.Favorite, contentDescription = stringResource(R.string.nav_favorites))
                        }
                    }
                    IconButton(onClick = onLanguageClick) {
                        Icon(Icons.Default.Language, contentDescription = stringResource(R.string.change_language))
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = stringResource(R.string.nav_settings))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor          = MaterialTheme.colorScheme.primary,
                    titleContentColor       = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor  = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val selectedIdx = CATEGORIES.indexOfFirst { it.key == selectedCategory }.coerceAtLeast(0)
            ScrollableTabRow(
                selectedTabIndex = selectedIdx,
                edgePadding      = 8.dp,
                containerColor   = MaterialTheme.colorScheme.surface,
                contentColor     = MaterialTheme.colorScheme.primary
            ) {
                CATEGORIES.forEach { cat ->
                    val selected = selectedCategory == cat.key
                    Tab(
                        selected = selected,
                        onClick  = { viewModel.selectCategory(cat.key) },
                        text     = {
                            Text(
                                text       = stringResource(cat.labelRes),
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            when (val state = listState) {
                is MovieListUiState.Loading -> LoadingContent()
                is MovieListUiState.Error   -> ErrorContent(state.message) { viewModel.refresh() }
                is MovieListUiState.Success -> MovieContent(
                    movies      = state.movies,
                    viewModel   = viewModel,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
private fun MovieContent(
    movies: List<Movie>,
    viewModel: MovieViewModel,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp),
        modifier       = Modifier.fillMaxSize()
    ) {
        if (movies.isNotEmpty()) {
            item {
                Text(
                    text     = stringResource(R.string.featured),
                    style    = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 8.dp)
                )
                MovieCarousel(movies = movies.take(6), onMovieClick = onMovieClick)
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                Text(
                    text     = "${stringResource(R.string.all_movies)} (${movies.size})",
                    style    = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 4.dp)
                )
            }
        }

        items(movies, key = { it.id }) { movie ->
            MovieListItem(
                movie           = movie,
                onMovieClick    = { onMovieClick(movie.id) },
                onFavoriteClick = { viewModel.toggleFavorite(movie) },
                isFavoriteFlow  = viewModel.isFavoriteFlow(movie.id)
            )
        }
    }
}

@Composable
private fun MovieCarousel(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyRow(
        contentPadding      = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies, key = { it.id }) { movie ->
            CarouselCard(movie = movie, onClick = { onMovieClick(movie.id) })
        }
    }
}

@Composable
private fun CarouselCard(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier  = Modifier
            .width(160.dp)
            .height(220.dp)
            .clickable { onClick() },
        shape     = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                model             = movie.posterUrl,
                contentDescription = movie.title,
                contentScale      = ContentScale.Crop,
                modifier          = Modifier.fillMaxSize()
            )
            Box(
                Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors  = listOf(Color.Transparent, Color.Black.copy(.80f)),
                        startY  = 120f
                    )
                )
            )
            Column(
                Modifier.align(Alignment.BottomStart).padding(10.dp)
            ) {
                Text(movie.title, color = Color.White, fontWeight = FontWeight.Bold,
                    fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(3.dp))
                    Text(movie.formattedRating, color = Color.White, fontSize = 11.sp)
                    Spacer(Modifier.width(6.dp))
                    Text(movie.releaseYear, color = Color.White.copy(.7f), fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun MovieListItem(
    movie: Movie,
    onMovieClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavoriteFlow: kotlinx.coroutines.flow.Flow<Boolean>
) {
    val isFav by isFavoriteFlow.collectAsState(initial = false)

    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable { onMovieClick() },
        shape     = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier          = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model              = movie.posterUrl,
                contentDescription = movie.title,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .width(72.dp).height(105.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        movie.title,
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        maxLines   = 2,
                        overflow   = TextOverflow.Ellipsis,
                        modifier   = Modifier.weight(1f)
                    )
                    IconButton(onClick = onFavoriteClick, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector        = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFav) stringResource(R.string.remove_from_favorites)
                            else stringResource(R.string.add_to_favorites),
                            tint               = if (isFav) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier           = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(Modifier.height(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(3.dp))
                    Text("${movie.formattedRating} (${movie.voteCount})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.Default.CalendarToday, null,
                        tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(2.dp))
                    Text(movie.releaseYear, style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.height(5.dp))
                Text(
                    movie.overview,
                    style    = MaterialTheme.typography.bodySmall,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3, overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.loading_movies), color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Icon(Icons.Default.ErrorOutline, null,
                tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(64.dp))
            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.failed_to_load), style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(message, style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(20.dp))
            Button(onClick = onRetry) {
                Icon(Icons.Default.Refresh, null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.retry))
            }
        }
    }
}