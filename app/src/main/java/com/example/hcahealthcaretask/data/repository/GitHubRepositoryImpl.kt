package com.example.hcahealthcaretask.data.repository

import com.example.hcahealthcaretask.model.RepositoryDataItem
import com.example.hcahealthcaretask.service.GitHubApiService
import javax.inject.Inject
import javax.inject.Singleton

/** Delegates repository requests to the GitHub API. */
@Singleton
class GitHubRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService
) : GitHubRepository {
    override suspend fun getRepositories(
        username: String,
        perPage: Int,
        page: Int
    ): List<RepositoryDataItem> =
        apiService.getRepositories(username, perPage, page)
}
