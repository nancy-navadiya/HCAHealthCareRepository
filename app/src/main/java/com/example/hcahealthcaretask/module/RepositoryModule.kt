package com.example.hcahealthcaretask.module

import com.example.hcahealthcaretask.data.repository.GitHubRepositoryImpl
import com.example.hcahealthcaretask.data.repository.GitHubRepository
import com.example.hcahealthcaretask.domain.usecase.GetRepositoriesUseCase
import com.example.hcahealthcaretask.domain.usecaseimpl.GetRepositoriesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Binds domain abstractions to their application implementations. */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindGitHubRepository(
        implementation: GitHubRepositoryImpl
    ): GitHubRepository

    @Binds
    abstract fun bindGetRepositoriesUseCase(
        implementation: GetRepositoriesUseCaseImpl
    ): GetRepositoriesUseCase
}
