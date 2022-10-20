package com.bikcodeh.mubi.domain.repository

import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.model.TvShowType

interface TvRepository {
    suspend fun getTvShows(tvShowType: TvShowType): Result<List<TVShow>>
}