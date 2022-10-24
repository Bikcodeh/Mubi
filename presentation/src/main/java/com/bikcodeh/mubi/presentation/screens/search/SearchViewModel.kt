package com.bikcodeh.mubi.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.mubi.domain.di.IoDispatcher
import com.bikcodeh.mubi.domain.usecase.SearchTvShowsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchTvShowsUC: SearchTvShowsUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _tvShows = MutableStateFlow<SearchState>(SearchState.Idle)
    val tvShows: StateFlow<SearchState>
        get() = _tvShows.asStateFlow()

    fun searchTvShows(query: String) {
        viewModelScope.launch(dispatcher) {
            searchTvShowsUC(query).collect {
                _tvShows.value = SearchState.Success(it)
            }
        }
    }
}