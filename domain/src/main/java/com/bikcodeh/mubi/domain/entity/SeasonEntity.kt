package com.bikcodeh.mubi.domain.entity

data class SeasonEntity(
    val id: Int,
    val name: String,
    val posterPath: String,
    val totalEpisodes: Int,
    val overview: String
)
