package com.bikcodeh.mubi.domain.model

data class Episode(
    val id: Int,
    val name: String,
    val seasonNumber: String,
    val overview: String,
    val episodeNumber: Int,
    val runtime: Int,
    val stillPath: String?
)
