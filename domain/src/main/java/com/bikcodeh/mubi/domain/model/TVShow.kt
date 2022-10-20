package com.bikcodeh.mubi.domain.model

data class TVShow(
    val backdropPath: String,
    val firstAirDate: String,
    val id: Int,
    val name: String,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    var isFavorite: Boolean = false,
    val category: String = ""
)
