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
    suspend fun searchTvShowsRemote(query: String): Result<List<TVShow>>
    suspend fun getDetailTvShow(tvShowId: String): Result<TVShow>
    suspend fun setAsFavorite(isFavorite: Boolean, tvShowId: String)
    suspend fun updateTvShow(tvShow: TVShow)
    suspend fun getTvShowByIdLocal(tvShowId: String): TVShow?
    suspend fun getFavoritesTvShows(): List<TVShow>
}