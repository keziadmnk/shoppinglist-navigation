package com.example.shoppinglist.screens

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R

@Composable
fun ProfileScreen() {
    val AVATAR_SIZE = 120.dp
    val BANNER_HEIGHT = 200.dp
    val CARD_CORNER = 24.dp

    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val avatarPainter = runCatching { painterResource(R.drawable.foto_profil_kezia) }.getOrNull()

    Box(Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(BANNER_HEIGHT)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(primary, secondary.copy(alpha = 0.8f))
                    )
                )
        )

        Card(
            shape = RoundedCornerShape(CARD_CORNER),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp)
                .padding(top = BANNER_HEIGHT - (AVATAR_SIZE / 2))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = (AVATAR_SIZE / 2) + 24.dp,
                        start = 16.dp, end = 16.dp, bottom = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Kezia Valerina Damanik",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(4.dp))
                Text("NIM 2311522010", color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(Modifier.height(16.dp))
                InfoRow(Icons.Filled.Person, "Hobi", "Main Piano")
                InfoRow(Icons.Filled.LocationOn, "Tempat Lahir", "Padang")
                InfoRow(Icons.Filled.DateRange, "Tanggal Lahir", "20 Juni 2005")
                InfoRow(Icons.Filled.School, "Peminatan", "Web Programming")
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = BANNER_HEIGHT - AVATAR_SIZE),
            contentAlignment = Alignment.TopCenter
        ) {
            Surface(
                shape = CircleShape,
                tonalElevation = 6.dp,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.size(AVATAR_SIZE)
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = avatarPainter != null,
                    enter = fadeIn(animationSpec = tween(250)),
                    exit = fadeOut(animationSpec = tween(250))
                ) {
                    Image(
                        painter = avatarPainter!!,
                        contentDescription = "Foto profil",
                        modifier = Modifier.fillMaxSize().clip(CircleShape)
                    )
                }

                if (avatarPainter == null) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(12.dp))
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.weight(1f))
        Text(value, fontWeight = FontWeight.SemiBold)
    }
    Divider(Modifier.padding(top = 8.dp))
}
