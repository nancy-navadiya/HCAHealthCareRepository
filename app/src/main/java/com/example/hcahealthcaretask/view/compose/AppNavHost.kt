package com.example.hcahealthcaretask.view.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hcahealthcaretask.navigation.REPOSITORIES_ROUTE
import com.example.hcahealthcaretask.navigation.REPOSITORY_DETAIL_SCREEN
import com.example.hcahealthcaretask.view.screens.GitHubRepositoriesScreen
import com.example.hcahealthcaretask.view.screens.RepositoryDetailScreen

@Composable
/** Defines navigation between the repository list and detail destinations. */
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = REPOSITORIES_ROUTE
    ) {
        composable(REPOSITORIES_ROUTE) {
            GitHubRepositoriesScreen(navController)
        }

        composable(
            route = REPOSITORY_DETAIL_SCREEN,
            arguments = listOf(
                navArgument("repositoryJson") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            // The selected repository is serialized so the detail screen is self-contained.
            val repositoryJson = backStackEntry.arguments?.getString("repositoryJson") ?: ""
            RepositoryDetailScreen(repositoryJson = repositoryJson, navController = navController)
        }
    }
}

