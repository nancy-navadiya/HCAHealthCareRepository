package com.example.hcahealthcaretask.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hcahealthcaretask.model.RepositoryDataItem
import com.example.hcahealthcaretask.service.GitHubApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/** Coordinates GitHub API access and exposes repositories as a Paging flow. */
class GitHubRepository @Inject constructor(
    private val apiService: GitHubApiService
) {
    /** Creates a new pager for the requested username. */
    fun getRepositories(username: String): Flow<PagingData<RepositoryDataItem>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GitHubPagingSource(apiService, username) }
        ).flow

    private class GitHubPagingSource(
        private val apiService: GitHubApiService,
        private val username: String
    ) : PagingSource<Int, RepositoryDataItem>() {
        /** Loads a page and converts API failures into Paging errors. */
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryDataItem> =
            try {
                val page = params.key ?: 1
                val repositories = apiService.getRepositories(username, PAGE_SIZE, page)
                LoadResult.Page(
                    data = repositories,
                    // GitHub returns fewer items on the final page.
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (repositories.size < PAGE_SIZE) null else page + 1
                )
            } catch (exception: Exception) {
                LoadResult.Error(exception)
            }

        /** Keeps the user's position when Paging refreshes the list. */
        override fun getRefreshKey(state: PagingState<Int, RepositoryDataItem>): Int? =
            state.anchorPosition?.let { position ->
                state.closestPageToPosition(position)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
            }
    }

    private companion object {
        const val PAGE_SIZE = 30
    }
}
