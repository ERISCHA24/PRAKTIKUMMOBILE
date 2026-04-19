package com.example.modul2compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KalkulatorTipCompose()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KalkulatorTipCompose() {
    var inputTagihan by remember { mutableStateOf("") }

    var dropdownExpanded by remember { mutableStateOf(false) }
    val daftarPilihan = listOf("15%" to 0.15, "18%" to 0.18, "20%" to 0.20)
    var pilihanTerpilih by remember { mutableStateOf(daftarPilihan[0]) }

    var switchBulat by remember { mutableStateOf(false) }
    var teksHasil by remember { mutableStateOf("Tip Amount: $0.00") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Calculate Tip",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TextField(
            value = inputTagihan,
            onValueChange = { inputTagihan = it },
            placeholder = { Text( text = "Bill Amount", fontSize = 18.sp,) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 8.dp),
            leadingIcon = {
                Text(
                    text = "$",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF3F3F6),
                focusedContainerColor = Color(0xFFF3F3F6),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded },
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            TextField(
                value = pilihanTerpilih.first,
                onValueChange = {},
                readOnly = true,
                leadingIcon = {
                    Text(
                        text = "%",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(60.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF3F3F6),
                    focusedContainerColor = Color(0xFFF3F3F6),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                daftarPilihan.forEach { pilihan ->
                    DropdownMenuItem(
                        text = { Text(pilihan.first) },
                        onClick = {
                            pilihanTerpilih = pilihan
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Round up tip?",
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = switchBulat,
                onCheckedChange = { switchBulat = it }
            )
        }

        Button(
            onClick = {
                val tagihan = inputTagihan.toDoubleOrNull() ?: 0.0

                val persentaseTip = pilihanTerpilih.second

                var jumlahTip = tagihan * persentaseTip

                if (switchBulat) {
                    jumlahTip = ceil(jumlahTip)
                }

                teksHasil = String.format("Tip Amount: $%.2f", jumlahTip)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tip Amount")
        }

        Text(
            text = teksHasil,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}