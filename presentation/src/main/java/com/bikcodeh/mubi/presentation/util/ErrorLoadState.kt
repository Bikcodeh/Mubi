package com.bikcodeh.mubi.presentation.util

import com.bikcodeh.mubi.domain.common.Error

data class ErrorLoadState(
    var error: Error? = null,
    var isAppend: Boolean = false,
    var isRefresh: Boolean = false
)