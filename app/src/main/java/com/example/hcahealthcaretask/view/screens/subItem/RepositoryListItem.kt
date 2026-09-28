package com.example.hcahealthcaretask.view.screens.subItem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hcahealthcaretask.model.RepositoryDataItem

@Composable
/** Renders one repository summary and handles clicks from the list screen. */
fun RepositoryListItem(
    repository: RepositoryDataItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = repository.owner.avatar_url,
                contentDescription = "Repository avatar",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .heightIn(min = 48.dp, max = 48.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = repository.name ?: "Name not available",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = repository.description ?: "No description available",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Language: ${repository.language ?: "English"}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Stars: ${repository.stargazers_count} • Forks: ${repository.forks_count}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
