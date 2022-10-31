package com.bikcodeh.mubi.presentation.util.base

import androidx.lifecycle.ViewModel
import com.bikcodeh.mubi.domain.R
import com.bikcodeh.mubi.domain.common.Error

open class BaseViewModel : ViewModel() {

    fun handleError(error: Error): ErrorState {
        return when (error) {
            Error.Connectivity -> ErrorState(
                errorMessage = R.string.connectivity_error,
                displayTryAgainBtn = true
            )
            is Error.HttpException -> ErrorState(
                errorMessage = error.messageResId,
                displayTryAgainBtn = true
            )
            is Error.Unknown -> ErrorState(
                errorMessage = R.string.unknown_error,
                displayTryAgainBtn = true
            )
            Error.InternetConnection -> ErrorState(
                errorMessage = R.string.internet_error,
                displayTryAgainBtn = true
            )
            is Error.NotFoundTvShow -> ErrorState(
                errorMessage = error.messageResId,
                displayTryAgainBtn = false,
                notFoundError = true
            )
        }
    }
}