package com.bikcodeh.mubi.domain.usecase

import com.bikcodeh.mubi.domain.repository.TvRepository
import javax.inject.Inject

class SearchTvShowsUC @Inject constructor(
    private val tvRepository: TvRepository
) {
    operator fun invoke(query: String) = tvRepository.searchTvShows(query)
}