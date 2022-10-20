package com.bikcodeh.mubi.data.remote

enum class TVEndpoint(val endpoint: String) {
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    ON_THE_AIR("on_the_air"),
    AIRING_TODAY("airing_today"),
    LATEST("latest")
}