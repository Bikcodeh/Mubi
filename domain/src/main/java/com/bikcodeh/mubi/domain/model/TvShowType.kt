package com.bikcodeh.mubi.domain.model

import androidx.annotation.StringRes
import com.bikcodeh.mubi.domain.R

enum class TvShowType(@StringRes val title: Int, val tvName: String) {
    TOP_RATED(title = R.string.tv_show_top_rated, tvName = "top_rated"),
    POPULAR(title = R.string.tv_show_popular, tvName = "popular"),
    ON_TV(title = R.string.tv_show_on_tv, tvName = "on_tv"),
    AIRING_TODAY(title = R.string.tv_show_airing_today, tvName = "airing_today")
}

fun getAllTvShowsTypes(): List<TvShowType> {
    return listOf(
        TvShowType.TOP_RATED,
        TvShowType.POPULAR,
        TvShowType.ON_TV,
        TvShowType.AIRING_TODAY
    )
}

fun getTvShowType(value: String): TvShowType? {
    val map = TvShowType.values().associateBy(TvShowType::tvName)
    return map[value]
}