package com.bikcodeh.mubi.data.remote.response

import com.squareup.moshi.Json

data class TVShowDTO(
    @Json(name = "backdrop_path")
    val backdrop_path: String,
    @Json(name = "first_air_date")
    val first_air_date: String,
    @Json(name = "genre_ids")
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    @Json(name = "origin_country")
    val origin_country: List<String>,
    @Json(name = "original_language")
    val original_language: String,
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
)