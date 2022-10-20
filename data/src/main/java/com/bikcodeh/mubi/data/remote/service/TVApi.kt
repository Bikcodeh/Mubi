package com.bikcodeh.mubi.data.remote.service

import com.bikcodeh.mubi.data.remote.response.TVResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface TVApi {

    @GET("popular")
    suspend fun getPopularTvShows(): Response<TVResponseDTO>

    @GET("top_rated")
    suspend fun getTopRatedTvShows(): Response<TVResponseDTO>

    @GET("airing_today")
    suspend fun getAiringTodayTvShows(): Response<TVResponseDTO>

    @GET("on_the_air")
    suspend fun getOnTheAirTvShows(): Response<TVResponseDTO>
}