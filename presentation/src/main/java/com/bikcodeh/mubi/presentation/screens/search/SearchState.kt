package com.bikcodeh.mubi.presentation.screens.search

import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.util.base.ErrorState

sealed class SearchState {
    object Idle : SearchState()
    object Loading : SearchState()
    data class Success(val tvShows: List<TVShow>) : SearchState()
    data class Failure(val error: ErrorState? = null) : SearchState()
}