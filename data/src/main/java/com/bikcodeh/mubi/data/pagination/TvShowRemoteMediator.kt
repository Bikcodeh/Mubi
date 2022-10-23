package com.bikcodeh.mubi.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bikcodeh.mubi.data.local.TvShowDatabase
import com.bikcodeh.mubi.data.mappers.TvShowMapper
import com.bikcodeh.mubi.data.remote.response.TVResponseDTO
import com.bikcodeh.mubi.data.remote.service.TVApi
import com.bikcodeh.mubi.domain.common.getSuccess
import com.bikcodeh.mubi.domain.common.makeSafeRequest
import com.bikcodeh.mubi.domain.entity.RemoteKeysEntity
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalPagingApi
class TvShowRemoteMediator(
    private val tvShowDatabase: TvShowDatabase,
    private val tvApi: TVApi,
    private val tvShowType: TvShowType,
    private val tvShowMapper: TvShowMapper
) : RemoteMediator<Int, TvShowEntity>() {

    private val tvShowDao = tvShowDatabase.tvShowDao()
    private val remoteKeysDao = tvShowDatabase.remoteKeysDao()


    override suspend fun initialize(): InitializeAction {
        val existData = withContext(Dispatchers.IO) {
            tvShowDao.existData(tvShowType.tvName)
        }
        return if (existData > 0) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvShowEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: TV_SHOW_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val request = when (tvShowType) {
                TvShowType.POPULAR -> handleRequest { tvApi.getPopularTvShows(page) }
                TvShowType.TOP_RATED -> handleRequest { tvApi.getTopRatedTvShows(page) }
                TvShowType.ON_TV -> handleRequest { tvApi.getOnTheAirTvShows(page) }
                TvShowType.AIRING_TODAY -> handleRequest { tvApi.getAiringTodayTvShows(page) }
            }

            val data = withContext(Dispatchers.IO) {
                makeSafeRequest { request }.getSuccess()
            }
            val tvShowsData = mutableListOf<TVShow>()
            data?.let {
                val tvShows = it.results.map { tvShowDTO -> tvShowDTO.toDomain(tvShowType.tvName) }
                tvShowsData.clear()
                tvShowsData.addAll(tvShows)
            }
            val endOfPagination = data?.totalPages == page

            tvShowDatabase.withTransaction {
                // Initial load of data
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys(tvShowType.tvName)
                    tvShowDao.clear(tvShowType.tvName)
                }

                val prevKey =
                    if (page == TV_SHOW_STARTING_PAGE_INDEX) null else page.minus(1)
                val nextKey = if (endOfPagination) null else page.plus(1)
                val tvShowsEntity = tvShowsData.map { tvShow -> tvShowMapper.map(tvShow) }
                val keys = tvShowsEntity.map {
                    RemoteKeysEntity(
                        tvShowId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        category = it.category
                    )
                }
                tvShowDao.addTvShows(tvShowsEntity)
                remoteKeysDao.insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun handleRequest(request: suspend () -> Response<TVResponseDTO>): Response<TVResponseDTO> {
        return withContext(Dispatchers.IO) {
            request()
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, TvShowEntity>): RemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { tvShow ->
                remoteKeysDao.remoteKeysTvShowId(tvShow.id, tvShow.category)
            }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, TvShowEntity>): RemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { tvShow ->
                // Get the remote keys of the first items retrieved
                remoteKeysDao.remoteKeysTvShowId(tvShow.id, tvShow.category)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TvShowEntity>
    ): RemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { tvShow ->
                remoteKeysDao.remoteKeysTvShowId(tvShow.id, tvShow.category)
            }
        }
    }

    companion object {
        private const val TV_SHOW_STARTING_PAGE_INDEX = 1
    }
}