package com.bikcodeh.mubi.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.bikcodeh.mubi.data.mappers.TvShowMapperEntity
import com.bikcodeh.mubi.domain.di.IoDispatcher
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.usecase.GetTvShowsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getTvShowsUC: GetTvShowsUC,
    private val tvShowMapperEntity: TvShowMapperEntity,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ViewModel() {

    val tvShows = getTvShowsUC(tvShowType = TvShowType.POPULAR)
        .cachedIn(viewModelScope)
        .flowOn(dispatcher)
        .map { page -> page.map { tvShowMapperEntity.map(it) } }
}