package com.example.hcahealthcaretask.viewmodel

import com.example.hcahealthcaretask.repository.GitHubRepository
import com.example.hcahealthcaretask.viewmodels.GitHubRepositoriesViewModel
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock

class GitHubRepositoriesViewModelTest {
    @Test
    fun `view model exposes a paging stream`() {
        val viewModel = GitHubRepositoriesViewModel(mock(GitHubRepository::class.java))

        assertNotNull(viewModel.repositories)
    }

    @Test
    fun `view model accepts username and language changes`() {
        val viewModel = GitHubRepositoriesViewModel(mock(GitHubRepository::class.java))

        viewModel.setUsername("android")
        viewModel.setLanguage("Kotlin")

        assertNotNull(viewModel.repositories)
    }
}
