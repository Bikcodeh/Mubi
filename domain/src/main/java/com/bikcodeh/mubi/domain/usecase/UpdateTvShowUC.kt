package com.bikcodeh.mubi.domain.usecase

import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.repository.TvRepository
import javax.inject.Inject

class UpdateTvShowUC @Inject constructor(
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(tvShow: TVShow) = tvRepository.updateTvShow(tvShow)
}