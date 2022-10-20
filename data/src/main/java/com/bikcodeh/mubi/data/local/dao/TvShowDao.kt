package com.bikcodeh.mubi.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bikcodeh.mubi.data.local.entity.TvShowEntity

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTvShows(tvShows: List<TvShowEntity>)

    @Query("SELECT * FROM tvshow WHERE category = :category")
    fun getTvShows(category: String): PagingSource<Int, TvShowEntity>

    @Query("UPDATE tvshow SET isFavorite = :isFavorite WHERE id = :tvShowId")
    suspend fun setIsFavorite(tvShowId: Int ,isFavorite: Boolean)
}