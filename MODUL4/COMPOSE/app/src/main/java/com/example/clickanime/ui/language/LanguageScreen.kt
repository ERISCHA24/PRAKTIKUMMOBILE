package com.example.clickanime.ui.language

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clickanime.R

data class LanguageOption(
    val code: String,
    val nameResId: Int,
    val nativeName: String,
    val flag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    onLanguageSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val currentLocale = context.resources.configuration.locales[0].language
    val languages = listOf(
        LanguageOption("en", R.string.lang_english, "English", "🇬🇧"),
        LanguageOption("id", R.string.lang_indonesian, "Bahasa Indonesia", "🇮🇩")
    )

    var selectedCode by remember { mutableStateOf(currentLocale) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.change_language), fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(24.dp)) {
            Icon(imageVector = Icons.Default.Language, contentDescription = null, modifier = Modifier.size(64.dp).align(Alignment.CenterHorizontally).padding(bottom = 8.dp), tint = MaterialTheme.colorScheme.primary)
            Text(text = stringResource(R.string.select_language), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 24.dp))

            languages.forEach { lang ->
                val isSelected = selectedCode == lang.code
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .border(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable { selectedCode = lang.code },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = lang.flag, fontSize = 32.sp, modifier = Modifier.padding(end = 16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = lang.nativeName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                            Text(text = stringResource(lang.nameResId), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        if (isSelected) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { onLanguageSelected(selectedCode) }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp)) {
                Text(text = stringResource(R.string.apply_language), fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
