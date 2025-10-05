package com.example.shoppinglist.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onClearAll: (() -> Unit)? = null
) {
    var notif by rememberSaveable { mutableStateOf(true) }
    var dynamicColor by rememberSaveable { mutableStateOf(true) }
    var showClearDialog by remember { mutableStateOf(false) }

    var language by rememberSaveable { mutableStateOf("Sistem") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Preferensi Aplikasi", style = MaterialTheme.typography.titleMedium)

        PreferenceSwitchRow(
            title = "Notifikasi",
            subtitle = "Aktifkan pengingat belanja harian",
            checked = notif,
            onCheckedChange = { notif = it }
        )

        PreferenceSwitchRow(
            title = "Dynamic Color",
            subtitle = "Ikuti warna sistem (Material You)",
            checked = dynamicColor,
            onCheckedChange = { dynamicColor = it }
        )

        PreferenceLanguageRow(
            title = "Bahasa",
            subtitle = "Bahasa antarmuka",
            selected = language,
            onSelected = { language = it }
        )

        Divider(Modifier.padding(top = 8.dp))

        Text("Data", style = MaterialTheme.typography.titleMedium)

        PreferenceDangerRow(
            title = "Bersihkan Semua Item",
            subtitle = "Hapus seluruh daftar belanja",
            onClick = { showClearDialog = true }
        )

        Divider(Modifier.padding(top = 8.dp))

        AboutCard()
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Hapus semua item?") },
            text = { Text("Tindakan ini tidak dapat dibatalkan.") },
            confirmButton = {
                TextButton(onClick = {
                    showClearDialog = false
                    onClearAll?.invoke()
                }) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) { Text("Batal") }
            }
        )
    }
}

@Composable
private fun PreferenceSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        ListItem(
            headlineContent = { Text(title, fontWeight = FontWeight.SemiBold) },
            supportingContent = { Text(subtitle) },
            trailingContent = {
                Switch(checked = checked, onCheckedChange = onCheckedChange)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreferenceLanguageRow(
    title: String,
    subtitle: String,
    selected: String,
    onSelected: (String) -> Unit
) {
    val options = listOf("Sistem", "Indonesia", "English")
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.fillMaxWidth().padding(12.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selected,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Pilih bahasa") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { opt ->
                        DropdownMenuItem(
                            text = { Text(opt) },
                            onClick = {
                                onSelected(opt)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferenceDangerRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        ListItem(
            headlineContent = { Text(title, fontWeight = FontWeight.SemiBold) },
            supportingContent = { Text(subtitle) },
            trailingContent = {
                IconButton(onClick = onClick) {
                    Icon(Icons.Outlined.DeleteForever, contentDescription = null)
                }
            }
        )
    }
}

@Composable
private fun AboutCard() {
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tentang Aplikasi", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                TextButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Sembunyikan" else "Selengkapnya")
                }
            }
            Text("Versi 1.0.0", style = MaterialTheme.typography.bodySmall)
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    "Aplikasi ini dibuat untuk tugas Navigasi: Drawer, Bottom Bar, " +
                            "Profile, dan Settings dengan animasi transisi.",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
