package com.example.hcahealthcaretask.view.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.hcahealthcaretask.navigation.REPOSITORY_DETAIL_ROUTE
import com.example.hcahealthcaretask.viewmodels.GitHubRepositoriesViewModel
import com.google.gson.Gson
import com.example.hcahealthcaretask.view.screens.dropdown.LanguageDropdown
import com.example.hcahealthcaretask.view.screens.subItem.RepositoryListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
        /** Displays username search, language filtering, and the paged repository list. */
fun GitHubRepositoriesScreen(navController: NavHostController) {
    val viewModel: GitHubRepositoriesViewModel = hiltViewModel()

    val repositories = viewModel.repositories.collectAsLazyPagingItems()

    val searchText = remember { mutableStateOf("") }
    val selectedLanguage = remember { mutableStateOf("All") }
    val languageOptions = remember(repositories.itemSnapshotList.items) {
        // Build filter choices from repositories currently loaded by Paging.
        listOf("All") + repositories.itemSnapshotList.items
            .mapNotNull { it.language }
            .filter { it.isNotBlank() }
            .distinct()
            .sorted()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "GitHub Repositories",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Gray.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = searchText.value,
                    onValueChange = {
                        searchText.value = it
                        viewModel.setUsername(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Search GitHub username") }
                )

                LanguageDropdown(
                    selectedLanguage = selectedLanguage.value,
                    options = languageOptions,
                    onLanguageSelected = { language ->
                        selectedLanguage.value = language
                        viewModel.setLanguage(language)
                    }
                )

                when {
                    repositories.loadState.refresh is androidx.paging.LoadState.Error -> {
                        Text(
                            text = "Error loading repositories",
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    repositories.loadState.refresh is androidx.paging.LoadState.Loading -> {
                        Text(text = "Loading repositories...")
                    }

                    repositories.itemCount == 0 -> {
                        Text(text = "No repositories found")
                    }

                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                count = repositories.itemCount,
                                key = repositories.itemKey { it.id }
                            ) { index ->
                                val repository = repositories[index] ?: return@items
                                RepositoryListItem(
                                    repository = repository,
                                    onClick = {
                                        // Encode JSON because repository data is passed through the route.
                                        val repositoryJson = Gson().toJson(repository)
                                        val encodedJson = Uri.encode(repositoryJson)
                                        navController.navigate("$REPOSITORY_DETAIL_ROUTE/$encodedJson")
                                    }
                                )
                            }
                            if (repositories.loadState.append is androidx.paging.LoadState.Loading) {
                                item { Text("Loading more repositories...") }
                            }
                        }
                    }
                }
            }
        }
    }
}