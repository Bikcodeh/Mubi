package com.bikcodeh.mubi.presentation.util.base

import androidx.annotation.StringRes

data class ErrorState(
    val displayTryAgainBtn: Boolean = false,
    val notFoundError: Boolean = false,
    @StringRes val errorMessage: Int
)