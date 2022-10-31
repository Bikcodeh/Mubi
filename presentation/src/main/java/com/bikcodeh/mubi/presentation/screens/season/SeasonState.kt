package com.bikcodeh.mubi.presentation.screens.season

import com.bikcodeh.mubi.domain.model.Season
import com.bikcodeh.mubi.presentation.util.base.ErrorState

data class SeasonState(
    val isLoading: Boolean = false,
    val season: Season? = null,
    val error: ErrorState? = null
)