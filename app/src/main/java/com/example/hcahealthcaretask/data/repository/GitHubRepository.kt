package com.example.hcahealthcaretask.data.repository

import com.example.hcahealthcaretask.model.RepositoryDataItem

/** Defines API-backed repository retrieval without exposing the Retrofit service. */
interface GitHubRepository {
    suspend fun getRepositories(
        username: String,
        perPage: Int,
        page: Int
    ): List<RepositoryDataItem>
}
