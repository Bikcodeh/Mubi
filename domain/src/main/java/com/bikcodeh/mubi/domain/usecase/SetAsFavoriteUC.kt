package com.bikcodeh.mubi.domain.usecase

import com.bikcodeh.mubi.domain.repository.TvRepository
import javax.inject.Inject

class SetAsFavoriteUC @Inject constructor(
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(isFavorite: Boolean, tvShowId: String) =
        tvRepository.setAsFavorite(isFavorite = isFavorite, tvShowId = tvShowId)
}