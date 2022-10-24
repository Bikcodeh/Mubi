package com.bikcodeh.mubi.domain.usecase

import com.bikcodeh.mubi.domain.repository.TvRepository
import javax.inject.Inject

class GetDetailTvShowUC @Inject constructor(
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(tvShowId: String) = tvRepository.getDetailTvShow(tvShowId)
}