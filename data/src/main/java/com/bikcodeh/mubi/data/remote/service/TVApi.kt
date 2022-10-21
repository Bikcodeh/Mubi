package com.bikcodeh.mubi.data.remote.service

import com.bikcodeh.mubi.data.remote.response.TVResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TVApi {

    @GET("popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int
    ): Response<TVResponseDTO>

    @GET("top_rated")
    suspend fun getTopRatedTvShows(
        @Query("page") page: Int
    ): Response<TVResponseDTO>

    @GET("airing_today")
    suspend fun getAiringTodayTvShows(
        @Query("page") page: Int
    ): Response<TVResponseDTO>

    @GET("on_the_air")
    suspend fun getOnTheAirTvShows(
        @Query("page") page: Int
    ): Response<TVResponseDTO>
}