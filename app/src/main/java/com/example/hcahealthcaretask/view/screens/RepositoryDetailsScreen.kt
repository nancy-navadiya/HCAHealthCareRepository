package com.example.hcahealthcaretask.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.hcahealthcaretask.model.RepositoryDataItem
import com.example.hcahealthcaretask.utils.Constants
import com.google.gson.Gson


@Composable
/** Shows the complete metadata for the repository selected from the list. */
fun RepositoryDetailScreen(
    repositoryJson: String,
    navController: NavHostController
) {
    if (repositoryJson.isBlank()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Repository details unavailable")
        }
        return
    }

    val repository = remember(repositoryJson) {
        // Parse only when the navigation argument changes, avoiding repeated work on recomposition.
        Gson().fromJson(repositoryJson, RepositoryDataItem::class.java)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }

        AsyncImage(
            model = repository.owner.avatar_url,
            contentDescription = "Repository avatar",
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.LightGray)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = repository.name ?: "",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(text = "${Constants.REPO_DES}${repository.description}")
        Text(text = "${Constants.REPO_LANG}${repository.language}")
        Text(text = "${Constants.REPO_OWNER}${repository.owner.login}")
        Text(text = "${Constants.REPO_OWNER_BTYPE}${repository.owner.type}")
        Text(text = "${Constants.REPO_CREATE}${repository.created_at}")
        Text(text = "${Constants.REPO_UPDATE}${repository.updated_at}")
        Text(text = "${Constants.REPO_STAG_COUNT}${repository.stargazers_count}")
        Text(text = "${Constants.REPO_FORKS_COUNT}${repository.forks_count}")
        Text(text = "${Constants.REPO_WATCHER_COUNT}${repository.watchers_count}")
        Text(text = "${Constants.REPO_MAIN_BRANCH}${repository.default_branch}")
    }
}
