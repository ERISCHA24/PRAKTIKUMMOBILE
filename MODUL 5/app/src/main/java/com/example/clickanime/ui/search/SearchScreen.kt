package com.example.clickanime.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.clickanime.R
import com.example.clickanime.model.Movie
import com.example.clickanime.viewmodel.MovieSearchUiState
import com.example.clickanime.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MovieViewModel,
    onMovieClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val query       by viewModel.searchQuery.collectAsState()
    val searchState by viewModel.searchState.collectAsState()
    val focusMgr    = LocalFocusManager.current

    DisposableEffect(Unit) { onDispose { viewModel.clearSearch() } }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.back), tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                title = {
                    OutlinedTextField(
                        value         = query,
                        onValueChange = { viewModel.updateSearch(it) },
                        placeholder   = { Text(stringResource(R.string.search_hint),
                            style = MaterialTheme.typography.bodyMedium) },
                        modifier      = Modifier.fillMaxWidth(),
                        singleLine    = true,
                        shape         = RoundedCornerShape(24.dp),
                        colors        = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor       = MaterialTheme.colorScheme.onPrimary,
                            unfocusedBorderColor     = MaterialTheme.colorScheme.onPrimary.copy(.5f),
                            focusedTextColor         = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor       = MaterialTheme.colorScheme.onPrimary,
                            cursorColor              = MaterialTheme.colorScheme.onPrimary,
                            focusedPlaceholderColor  = MaterialTheme.colorScheme.onPrimary.copy(.7f),
                            unfocusedPlaceholderColor= MaterialTheme.colorScheme.onPrimary.copy(.7f)
                        ),
                        trailingIcon  = {
                            if (query.isNotBlank()) {
                                IconButton(onClick = { viewModel.clearSearch() }) {
                                    Icon(Icons.Default.Clear, stringResource(R.string.clear_search),
                                        tint = MaterialTheme.colorScheme.onPrimary)
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { focusMgr.clearFocus() })
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (val state = searchState) {
                is MovieSearchUiState.Idle -> {
                    EmptyPlaceholder(Icons.Default.Search, stringResource(R.string.search_empty_hint))
                }
                is MovieSearchUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is MovieSearchUiState.Empty -> {
                    EmptyPlaceholder(Icons.Default.SearchOff,
                        "${stringResource(R.string.search_no_results)} \"$query\"")
                }
                is MovieSearchUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                            Icon(Icons.Default.ErrorOutline, null,
                                tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(56.dp))
                            Spacer(Modifier.height(12.dp))
                            Text(state.message, textAlign = TextAlign.Center)
                        }
                    }
                }
                is MovieSearchUiState.Success -> {
                    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                        item {
                            Text(
                                text     = "${state.movies.size} ${stringResource(R.string.search_results)} \"$query\"",
                                style    = MaterialTheme.typography.bodySmall,
                                color    = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }
                        items(state.movies, key = { it.id }) { movie ->
                            SearchResultItem(movie = movie, onClick = { onMovieClick(movie.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyPlaceholder(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Icon(icon, null, modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(.35f))
            Spacer(Modifier.height(16.dp))
            Text(label, style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(.6f), textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun SearchResultItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier  = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp).clickable { onClick() },
        shape     = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model              = movie.posterUrl,
                contentDescription = movie.title,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier.width(55.dp).height(80.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(movie.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(13.dp))
                    Spacer(Modifier.width(3.dp))
                    Text(movie.formattedRating, style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.width(8.dp))
                    Text(movie.releaseYear, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.height(4.dp))
                Text(movie.overview, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
            Icon(Icons.Default.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}