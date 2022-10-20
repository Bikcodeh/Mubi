package com.bikcodeh.mubi.data.remote.service

import com.bikcodeh.mubi.data.remote.response.TVResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieApi {

    @GET
    suspend fun getTvShow(
        @Url url: String
    ): Response<TVResponseDTO>
}