package com.bikcodeh.mubi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshow")
data class TvShowEntity(
    val backdropPath: String,
    val firstAirDate: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val isFavorite: Boolean,
    val category: String
)