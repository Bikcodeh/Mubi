package com.bikcodeh.mubi.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bikcodeh.mubi.domain.di.IoDispatcher
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.usecase.GetTvShowsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getTvShowsUC: GetTvShowsUC,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ViewModel() {

    val tvShows = getTvShowsUC(tvShowType = TvShowType.POPULAR)
        .cachedIn(viewModelScope)
        .flowOn(dispatcher)
}