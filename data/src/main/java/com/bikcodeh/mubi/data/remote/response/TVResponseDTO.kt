package com.bikcodeh.mubi.data.remote.response

import com.squareup.moshi.Json

data class TVResponseDTO(
    val page: Int,
    val results: List<TVShowDTO>,
    @Json(name = "total_pages")
    val totalPages: Int
)