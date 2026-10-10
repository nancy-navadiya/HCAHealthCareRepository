package com.example.hcahealthcaretask.domain.usecase

import androidx.paging.PagingData
import com.example.hcahealthcaretask.model.RepositoryDataItem
import kotlinx.coroutines.flow.Flow

/** Defines the repository-search operation used by the presentation layer. */
interface GetRepositoriesUseCase {
    operator fun invoke(
        username: String,
        language: String? = null
    ): Flow<PagingData<RepositoryDataItem>>
}
