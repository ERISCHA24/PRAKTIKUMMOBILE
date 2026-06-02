package com.example.clickanime.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.clickanime.R
import com.example.clickanime.model.MovieDetail
import com.example.clickanime.viewmodel.MovieDetailUiState
import com.example.clickanime.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    viewModel: MovieViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.detailState.collectAsState()

    LaunchedEffect(movieId) { viewModel.loadDetail(movieId) }
    DisposableEffect(Unit) { onDispose { viewModel.clearDetail() } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = (uiState as? MovieDetailUiState.Success)?.movie?.title ?: ""
                    Text(title, fontWeight = FontWeight.Bold, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                },
                actions = {
                    if (uiState is MovieDetailUiState.Success) {
                        val movie = (uiState as MovieDetailUiState.Success).movie
                        val isFav by viewModel.isFavoriteFlow(movie.id).collectAsState(false)
                        IconButton(onClick = { viewModel.toggleFavorite(movie) }) {
                            Icon(
                                imageVector        = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isFav) stringResource(R.string.remove_from_favorites)
                                else stringResource(R.string.add_to_favorites),
                                tint               = if (isFav) Color.Red else MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor         = MaterialTheme.colorScheme.primary,
                    titleContentColor      = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is MovieDetailUiState.Loading, MovieDetailUiState.Idle -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is MovieDetailUiState.Error -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                        Icon(Icons.Default.ErrorOutline, null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(56.dp))
                        Spacer(Modifier.height(12.dp))
                        Text(state.message, textAlign = TextAlign.Center)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadDetail(movieId) }) { Text(stringResource(R.string.retry)) }
                    }
                }
            }
            is MovieDetailUiState.Success -> {
                DetailContent(movie = state.movie, paddingValues = padding)
            }
        }
    }
}

@Composable
private fun DetailContent(movie: MovieDetail, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Box(Modifier.fillMaxWidth().height(260.dp)) {
            AsyncImage(
                model              = movie.backdropUrl ?: movie.posterUrl,
                contentDescription = movie.title,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier.fillMaxSize()
            )
            Box(Modifier.fillMaxSize().background(
                Brush.verticalGradient(listOf(Color.Transparent, MaterialTheme.colorScheme.background))
            ))
            Column(Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                Text(movie.title, color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                if (movie.tagline.isNotBlank()) {
                    Text("\"${movie.tagline}\"", color = MaterialTheme.colorScheme.primary,
                        fontSize = 13.sp, modifier = Modifier.padding(top = 2.dp))
                }
            }
        }

        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp).offset(y = (-20).dp)) {
            AsyncImage(
                model              = movie.posterUrl,
                contentDescription = movie.title,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier.width(100.dp).height(148.dp).clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.width(14.dp))
            Card(
                modifier = Modifier.weight(1f).align(Alignment.Bottom),
                shape    = RoundedCornerShape(12.dp),
                colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatRow(Icons.Default.Star,          stringResource(R.string.rating),  "${movie.formattedRating}/10", Color(0xFFFFD700))
                    StatRow(Icons.Default.Schedule,      stringResource(R.string.runtime), movie.formattedRuntime, MaterialTheme.colorScheme.primary)
                    StatRow(Icons.Default.CalendarToday, stringResource(R.string.year),    movie.releaseYear, MaterialTheme.colorScheme.secondary)
                    StatRow(Icons.Default.HowToVote,     stringResource(R.string.votes),   movie.voteCount.toString(), MaterialTheme.colorScheme.tertiary)
                }
            }
        }

        Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            if (movie.genres.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 10.dp)) {
                    movie.genres.take(4).forEach { genre ->
                        Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.secondaryContainer) {
                            Text(genre.name, modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            if (movie.status.isNotBlank()) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                    Icon(Icons.Default.Info, null, Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(4.dp))
                    Text("${stringResource(R.string.status)}: ${movie.status}",
                        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(12.dp))
                    Text("${stringResource(R.string.language)}: ${movie.originalLanguage.uppercase()}",
                        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Text(stringResource(R.string.overview), style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Text(
                text       = movie.overview.ifBlank { stringResource(R.string.no_overview) },
                style      = MaterialTheme.typography.bodyMedium,
                color      = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp,
                modifier   = Modifier.padding(bottom = 20.dp)
            )

            if (movie.budget > 0 || movie.revenue > 0) {
                Text(stringResource(R.string.box_office), style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                        BoxOfficeCol(stringResource(R.string.budget),  formatCurrency(movie.budget))
                        VerticalDivider(Modifier.height(40.dp))
                        BoxOfficeCol(stringResource(R.string.revenue), formatCurrency(movie.revenue))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StatRow(icon: ImageVector, label: String, value: String, tint: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = tint, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(6.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun BoxOfficeCol(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

private fun formatCurrency(amount: Long): String = when {
    amount == 0L            -> "N/A"
    amount >= 1_000_000_000 -> "$%.1fB".format(amount / 1_000_000_000.0)
    amount >= 1_000_000     -> "$%.1fM".format(amount / 1_000_000.0)
    else                    -> "$$amount"
}