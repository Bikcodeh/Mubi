package com.bikcodeh.mubi.domain.usecase

import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.repository.TvRepository
import javax.inject.Inject

class GetTvShowsUC @Inject constructor(
    private val tvRepository: TvRepository
) {
    operator fun invoke(tvShowType: TvShowType) = tvRepository.getTvShows(tvShowType)
}