package com.bikcodeh.mubi.data.remote.service

import com.bikcodeh.mubi.data.remote.response.SeasonDTO
import com.bikcodeh.mubi.data.remote.response.TVResponseDTO
import com.bikcodeh.mubi.data.remote.response.TVShowDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("search/tv")
    suspend fun searchTvShow(
        @Query("query") query: String
    ): Response<TVResponseDTO>

    @GET("{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") tvShowId: String
    ): Response<TVShowDTO>

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getDetailSeason(
        @Path("tv_id") tvShowId: String,
        @Path("season_number") seasonNumber: Int
    ): Response<SeasonDTO>
}