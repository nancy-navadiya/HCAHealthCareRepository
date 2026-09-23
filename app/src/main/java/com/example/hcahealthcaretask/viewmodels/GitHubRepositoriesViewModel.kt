package com.example.hcahealthcaretask.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.cachedIn
import com.example.hcahealthcaretask.model.RepositoryDataItem
import com.example.hcahealthcaretask.repository.GitHubRepository
import com.example.hcahealthcaretask.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
/** Holds repository search/filter state and exposes the resulting paged data. */
class GitHubRepositoriesViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {
    private val username = MutableStateFlow(Constants.DEFAULT_USERNAME)
    private val selectedLanguage = MutableStateFlow(ALL_LANGUAGES)

    // Debouncing prevents a network request for every character entered in the search field.
    val repositories: StateFlow<PagingData<RepositoryDataItem>> =
        combine(
            username.debounce(1000).distinctUntilChanged(),
            selectedLanguage
        ) { currentUsername, language -> currentUsername to language }
            .flatMapLatest { (currentUsername, language) ->
                // A new username or language selection cancels the previous stream.
                repository.getRepositories(currentUsername).map { pagingData ->
                    if (language == ALL_LANGUAGES) {
                        pagingData
                    } else {
                        pagingData.filter {
                            it.language.equals(language, ignoreCase = true)
                        }
                    }
                }
            }
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                PagingData.empty()
            )

    /** Updates the username, falling back to the default when the input is blank. */
    fun setUsername(value: String) {
        username.value = value.trim().ifBlank { Constants.DEFAULT_USERNAME }
    }

    /** Applies the language selected by the user to the current paged stream. */
    fun setLanguage(language: String) {
        selectedLanguage.value = language
    }

    companion object {
        /** Sentinel value meaning that no language filter should be applied. */
        const val ALL_LANGUAGES = "All"
    }
}
