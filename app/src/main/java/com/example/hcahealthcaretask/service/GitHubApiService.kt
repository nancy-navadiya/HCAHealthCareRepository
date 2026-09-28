package com.example.hcahealthcaretask.service

import com.example.hcahealthcaretask.model.RepositoryDataItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** Retrofit endpoints used to retrieve repositories for a GitHub user. */
interface GitHubApiService {
    /** Loads one page of repositories using GitHub's pagination parameters. */
    @GET("users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") username: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<RepositoryDataItem>
}
