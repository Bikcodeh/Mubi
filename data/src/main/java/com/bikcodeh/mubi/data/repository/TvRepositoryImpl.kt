package com.bikcodeh.mubi.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bikcodeh.mubi.data.local.db.TvShowDatabase
import com.bikcodeh.mubi.data.local.db.dao.TvShowDao
import com.bikcodeh.mubi.data.mappers.TvShowMapper
import com.bikcodeh.mubi.data.mappers.TvShowMapperEntity
import com.bikcodeh.mubi.data.pagination.TvShowRemoteMediator
import com.bikcodeh.mubi.data.remote.service.TVApi
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class TvRepositoryImpl @Inject constructor(
    private val tvShowDao: TvShowDao,
    private val tvShowDatabase: TvShowDatabase,
    private val tvApi: TVApi,
    private val tvShowMapper: TvShowMapper,
    private val tvShowMapperEntity: TvShowMapperEntity
) : TvRepository {

    override fun getTvShows(tvShowType: TvShowType): Flow<PagingData<TvShowEntity>> {

        val tvShowSourceFactory = { tvShowDao.getTvShows(tvShowType.tvName) }

        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
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

    override fun searchTvShows(query: String): Flow<List<TVShow>> {
        return tvShowDao.searchTvShows("%$query%")
            .map { data -> data.map { tvShowEntity -> tvShowMapperEntity.map(tvShowEntity) } }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}