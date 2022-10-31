package com.bikcodeh.mubi.data.remote.response

import com.bikcodeh.mubi.domain.model.Episode
import com.squareup.moshi.Json

data class EpisodeDTO(
    val id: Int,
    val name: String,
    @Json(name = "season_number")
    val seasonNumber: String,
    val overview: String,
    @Json(name = "episode_number")
    val episodeNumber: Int,
    val runtime: Int,
    @Json(name = "still_path")
    val stillPath: String? = ""
) {
    fun toDomain() = Episode(
        id, name, seasonNumber, overview, episodeNumber, runtime, stillPath
    )
}
