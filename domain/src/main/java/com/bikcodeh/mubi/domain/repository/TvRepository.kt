package com.bikcodeh.mubi.domain.repository

import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.model.TVShow

interface TvRepository {
    suspend fun getLatestTvShows(): Result<List<TVShow>>
}