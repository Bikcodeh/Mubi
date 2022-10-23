package com.bikcodeh.mubi.domain.repository

import androidx.paging.PagingData
import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import kotlinx.coroutines.flow.Flow

interface TvRepository {
    fun getTvShows(tvShowType: TvShowType): Flow<PagingData<TvShowEntity>>
    fun searchTvShows(query: String): Flow<List<TVShow>>
}