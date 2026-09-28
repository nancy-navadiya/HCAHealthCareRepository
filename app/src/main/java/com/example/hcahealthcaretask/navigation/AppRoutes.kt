package com.example.hcahealthcaretask.navigation

/** Route displaying the paged list of repositories. */
const val REPOSITORIES_ROUTE = "repositories"

/** Base route used when navigating to a repository detail screen. */
const val REPOSITORY_DETAIL_ROUTE = "repositoryDetail"

/** Detail route with the selected repository encoded as a navigation argument. */
const val REPOSITORY_DETAIL_SCREEN = "$REPOSITORY_DETAIL_ROUTE/{repositoryJson}"
