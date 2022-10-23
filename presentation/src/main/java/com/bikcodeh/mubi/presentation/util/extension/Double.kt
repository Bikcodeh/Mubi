package com.bikcodeh.mubi.presentation.util.extension

import com.bikcodeh.mubi.presentation.util.Constants

fun Double.validateRating(): Double {
    return if (this > Constants.MAXIMUM_AVERAGE) Constants.MAXIMUM_AVERAGE else this
}