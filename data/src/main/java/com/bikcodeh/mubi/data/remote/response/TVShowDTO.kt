package com.bikcodeh.mubi.data.remote.response

import com.bikcodeh.mubi.domain.model.TVShow
import com.squareup.moshi.Json

data class TVShowDTO(
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "first_air_date")
    val firstAirDate: String,
    val id: Int,
    val name: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
) {
    fun toDomain(): TVShow = TVShow(
        backdropPath,
        firstAirDate,
        id,
        name,
        originalLanguage,
        originalName,
        overview,
        popularity,
        posterPath,
        voteAverage,
        voteCount
    )
}