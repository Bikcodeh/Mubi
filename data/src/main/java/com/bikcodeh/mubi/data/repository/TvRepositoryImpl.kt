package com.bikcodeh.mubi.data.repository

import com.bikcodeh.mubi.data.remote.service.TVApi
import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.common.fold
import com.bikcodeh.mubi.domain.common.makeSafeRequest
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.repository.TvRepository
import javax.inject.Inject

class TvRepositoryImpl @Inject constructor(
    private val tvApi: TVApi
) : TvRepository {

    override suspend fun getTvShows(tvShowType: TvShowType): Result<List<TVShow>> {

        val request = when (tvShowType) {
            TvShowType.POPULAR -> tvApi.getPopularTvShows()
            TvShowType.TOP_RATED -> tvApi.getTopRatedTvShows()
            TvShowType.ON_TV -> tvApi.getOnTheAirTvShows()
            TvShowType.AIRING_TODAY -> tvApi.getAiringTodayTvShows()
        }

        val response = makeSafeRequest { request }

        return response.fold(
            onSuccess = {
                Result.Success(it.results.map { tvShowDTO -> tvShowDTO.toDomain() })
            },
            onException = {
                Result.Exception(it)
            },
            onError = { code, message ->
                Result.Error(code, message)
            }
        )
    }
}