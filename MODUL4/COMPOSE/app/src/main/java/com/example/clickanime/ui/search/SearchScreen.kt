package com.example.clickanime.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clickanime.viewmodel.AnimeListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    viewModel: AnimeListViewModel
) {
    val characters by viewModel.characters.collectAsState()
    var query by remember { mutableStateOf("") }
    val results = remember(query, characters) {
        if (query.isBlank()) characters else characters.filter {
            it.year.toString().contains(query) || it.rating.toString().contains(query)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Search by year or rating (demo)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Results: ${results.size}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            results.forEach { item ->
                Text(text = "${item.id} • ${item.year} • ${item.rating}", modifier = Modifier.padding(vertical = 6.dp))
                Divider()
            }
        }
    }
}
