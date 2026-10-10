package com.example.hcahealthcaretask.domain.usecaseimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.filter
import com.example.hcahealthcaretask.model.RepositoryDataItem
import com.example.hcahealthcaretask.data.repository.GitHubRepository
import com.example.hcahealthcaretask.domain.usecase.GetRepositoriesUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** Applies repository search rules and paging to data from the repository. */
class GetRepositoriesUseCaseImpl @Inject constructor(
    private val repository: GitHubRepository
) : GetRepositoriesUseCase {
    override fun invoke(
        username: String,
        language: String?
    ): Flow<PagingData<RepositoryDataItem>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GitHubPagingSource(repository, username) }
        ).flow.map { pagingData ->
            if (language.isNullOrBlank()) {
                pagingData
            } else {
                pagingData.filter { it.language.equals(language, ignoreCase = true) }
            }
        }

    private class GitHubPagingSource(
        private val repository: GitHubRepository,
        private val username: String
    ) : PagingSource<Int, RepositoryDataItem>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryDataItem> =
            try {
                val page = params.key ?: 1
                val repositories = repository.getRepositories(username, PAGE_SIZE, page)
                LoadResult.Page(
                    data = repositories,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (repositories.size < PAGE_SIZE) null else page + 1
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                LoadResult.Error(exception)
            }

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
