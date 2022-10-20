package com.bikcodeh.mubi.domain.repository

import com.bikcodeh.mubi.domain.common.Result
import com.bikcodeh.mubi.domain.model.TVShow

interface MovieRepository {
    suspend fun getTvShow(url: String): Result<List<TVShow>>
}