package com.bikcodeh.mubi.presentation.screens.detail

import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.util.base.ErrorState

data class DetailState(
    val isLoading: Boolean = false,
    val tvShow: TVShow? = null,
    val error: ErrorState? = null
)