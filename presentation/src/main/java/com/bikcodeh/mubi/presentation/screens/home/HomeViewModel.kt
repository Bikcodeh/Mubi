package com.bikcodeh.mubi.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.bikcodeh.mubi.data.mappers.TvShowMapperEntity
import com.bikcodeh.mubi.domain.di.IoDispatcher
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.usecase.GetTvShowsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTvShowsUC: GetTvShowsUC,
    private val tvShowMapperEntity: TvShowMapperEntity,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val tvShows = MutableStateFlow<Flow<PagingData<TVShow>>>(flowOf(PagingData.empty<TVShow>()))

    fun searchTvShows(tvShowType: TvShowType) {
        tvShows.value = getTvShowsUC(tvShowType = tvShowType)
            .cachedIn(viewModelScope)
            .map { page -> page.map { tvShowMapperEntity.map(it) } }
    }
}