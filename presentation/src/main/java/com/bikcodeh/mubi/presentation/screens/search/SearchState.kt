package com.bikcodeh.mubi.presentation.screens.search

import androidx.annotation.StringRes
import com.bikcodeh.mubi.domain.model.TVShow

sealed class SearchState {
    object Idle : SearchState()
    object Loading: SearchState()
    data class Success(val tvShows: List<TVShow>) : SearchState()
    data class Failure(val error: Error? = null): SearchState()
}

data class Error(
    val displayTryAgainBtn: Boolean = false,
    val notFoundError: Boolean = false,
    @StringRes val errorMessage: Int
)