package com.bikcodeh.mubi.data.remote.service

import com.bikcodeh.mubi.data.remote.response.TVResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TVApi {

    @GET("latest")
    suspend fun getTvShow(
        @Query("api_key") apiKey: String = "4662e7a7fe13c9d91c80552e10a09dc1"
    ): Response<TVResponseDTO>
}