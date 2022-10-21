package com.bikcodeh.mubi.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bikcodeh.mubi.data.local.TvShowDatabase
import com.bikcodeh.mubi.data.local.dao.TvShowDao
import com.bikcodeh.mubi.data.mappers.TvShowMapper
import com.bikcodeh.mubi.data.pagination.TvShowRemoteMediator
import com.bikcodeh.mubi.data.remote.service.TVApi
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class TvRepositoryImpl @Inject constructor(
    private val tvShowDao: TvShowDao,
    private val tvShowDatabase: TvShowDatabase,
    private val tvApi: TVApi,
    private val tvShowMapper: TvShowMapper
) : TvRepository {

    override fun getTvShows(tvShowType: TvShowType): Flow<PagingData<TvShowEntity>> {

        val tvShowSourceFactory = { tvShowDao.getTvShows(tvShowType.name) }

        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            null,
            TvShowRemoteMediator(
                tvShowDatabase = tvShowDatabase,
                tvApi = tvApi,
                tvShowType = tvShowType,
                tvShowMapper = tvShowMapper
            ),
            tvShowSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 3
    }
}