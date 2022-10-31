package com.bikcodeh.mubi.data.remote.response

import com.bikcodeh.mubi.domain.model.Season
import com.squareup.moshi.Json

data class SeasonDTO(
    val id: Int,
    val name: String,
    @Json(name = "poster_path")
    val posterPath: String? = "",
    @Json(name = "episode_count")
    val totalEpisodes: Int,
    val overview: String,
    val episodes: List<EpisodeDTO>? = null
) {
    fun toDomain(): Season = Season(
        id,
        name,
        posterPath,
        totalEpisodes,
        overview,
        episodes?.map { it.toDomain() }
    )
}
