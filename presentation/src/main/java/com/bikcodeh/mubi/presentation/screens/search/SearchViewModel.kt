package com.bikcodeh.mubi.presentation.screens.search

import androidx.lifecycle.viewModelScope
import com.bikcodeh.mubi.domain.common.fold
import com.bikcodeh.mubi.domain.common.toError
import com.bikcodeh.mubi.domain.common.validateHttpErrorCode
import com.bikcodeh.mubi.domain.di.IoDispatcher
import com.bikcodeh.mubi.domain.usecase.SearchTvShowsRemoteUC
import com.bikcodeh.mubi.domain.usecase.SearchTvShowsUC
import com.bikcodeh.mubi.presentation.util.base.BaseViewModel
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
    private val searchTvShowsRemoteUC: SearchTvShowsRemoteUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _searchTvShowsState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchTvShowsState: StateFlow<SearchState>
        get() = _searchTvShowsState.asStateFlow()

    fun searchTvShows(query: String) {
        viewModelScope.launch(dispatcher) {
            searchTvShowsUC(query).collect {
                if (it.isNotEmpty()) {
                    _searchTvShowsState.value = SearchState.Success(it)
                } else {
                    searchTvShowsRemote(query)
                }
            }
        }
    }

    fun searchTvShowsRemote(query: String) {
        _searchTvShowsState.value = SearchState.Loading
        viewModelScope.launch(dispatcher) {
            searchTvShowsRemoteUC(query).fold(
                onSuccess = {
                    _searchTvShowsState.value = SearchState.Success(it)
                },
                onError = { code, _ ->
                    val error = handleError(code.validateHttpErrorCode())
                    _searchTvShowsState.value = SearchState.Failure(error)
                },
                onException = {
                    val error = handleError(it.toError())
                    _searchTvShowsState.value = SearchState.Failure(error)
                }
            )
        }
    }
}