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
import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.common.fold
import com.bikcodeh.mubi.domain.common.makeSafeRequest
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

    override suspend fun searchTvShowsRemote(query: String): Result<List<TVShow>> {
        val response = makeSafeRequest { tvApi.searchTvShow(query) }
        return response.fold(
            onSuccess = {
                Result.Success(it.results.map { tvShowDTO -> tvShowDTO.toDomain() })
            },
            onError = { code, message ->
                Result.Error(code, message)
            },
            onException = {
                Result.Exception(it)
            }
        )
    }

    override suspend fun getDetailTvShow(tvShowId: String): Result<TVShow> {
        val response = makeSafeRequest { tvApi.getDetailTvShow(tvShowId) }
        return response.fold(
            onSuccess = {
                Result.Success(it.toDomain())
            },
            onError = { code, message ->
                Result.Error(code, message)
            },
            onException = {
                Result.Exception(it)
            }
        )
    }

    override suspend fun setAsFavorite(isFavorite: Boolean, tvShowId: String) {
        tvShowDao.setIsFavorite(tvShowId = tvShowId, isFavorite = isFavorite)
    }

    override suspend fun updateTvShow(tvShow: TVShow) {
        tvShowDao.updateTvShow(tvShowMapper.map(tvShow))
    }

    override suspend fun getTvShowByIdLocal(tvShowId: String): TVShow {
        return tvShowMapperEntity.map(tvShowDao.getTvShowById(tvShowId))
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}