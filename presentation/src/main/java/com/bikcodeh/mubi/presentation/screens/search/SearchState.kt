package com.bikcodeh.mubi.presentation.screens.search

import com.bikcodeh.mubi.domain.model.TVShow

sealed class SearchState {
    object Idle : SearchState()
    data class Success(val tvShows: List<TVShow>) : SearchState()
}