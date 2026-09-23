package com.example.hcahealthcaretask.viewmodel

import com.example.hcahealthcaretask.repository.GitHubRepository
import com.example.hcahealthcaretask.viewmodels.GitHubRepositoriesViewModel
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock

class GitHubRepositoriesViewModelFactoryTest {
    @Test
    fun `view model can be constructed with its repository dependency`() {
        val viewModel = GitHubRepositoriesViewModel(mock(GitHubRepository::class.java))

        assertNotNull(viewModel)
    }
}
