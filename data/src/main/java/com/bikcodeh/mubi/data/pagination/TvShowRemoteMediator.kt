package com.bikcodeh.mubi.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bikcodeh.mubi.data.local.TvShowDatabase
import com.bikcodeh.mubi.data.mappers.TvShowMapper
import com.bikcodeh.mubi.data.remote.service.TVApi
import com.bikcodeh.mubi.domain.common.getSuccess
import com.bikcodeh.mubi.domain.common.makeSafeRequest
import com.bikcodeh.mubi.domain.entity.RemoteKeysEntity
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import retrofit2.HttpException
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
        return InitializeAction.LAUNCH_INITIAL_REFRESH
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
                TvShowType.POPULAR -> tvApi.getPopularTvShows(page)
                TvShowType.TOP_RATED -> tvApi.getTopRatedTvShows(page)
                TvShowType.ON_TV -> tvApi.getOnTheAirTvShows(page)
                TvShowType.AIRING_TODAY -> tvApi.getAiringTodayTvShows(page)
            }

            val data = makeSafeRequest { request }.getSuccess()
            val tvShows = mutableListOf<TVShow>()
            data?.let {
                tvShows.clear()
                tvShows.addAll(it.results.map { tvShowDTO -> tvShowDTO.toDomain(tvShowType.name) })
            }
            val endOfPagination = data?.page == data?.totalPages

            tvShowDatabase.withTransaction {
                // Initial load of data
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    tvShowDao.clear()
                }

                val prevKey = if (page == TV_SHOW_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1
                val keys = tvShows.map {
                    RemoteKeysEntity(
                        tvShowId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                remoteKeysDao.insertAll(keys)
                tvShowDao.addTvShows(tvShows.map { tvShow -> tvShowMapper.map(tvShow) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, TvShowEntity>): RemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { tvShow ->
                remoteKeysDao.remoteKeysTvShowId(tvShow.id)
            }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, TvShowEntity>): RemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { tvShow ->
                // Get the remote keys of the first items retrieved
                remoteKeysDao.remoteKeysTvShowId(tvShow.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TvShowEntity>
    ): RemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                remoteKeysDao.remoteKeysTvShowId(repoId)
            }
        }
    }

    companion object {
        private const val TV_SHOW_STARTING_PAGE_INDEX = 1
    }
}