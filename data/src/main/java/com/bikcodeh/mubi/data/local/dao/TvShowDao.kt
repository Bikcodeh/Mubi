package com.bikcodeh.mubi.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bikcodeh.mubi.domain.entity.TvShowEntity

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvShows(tvShows: List<TvShowEntity>)

    @Query("SELECT * FROM tvshow WHERE category = :category")
    fun getTvShows(category: String): PagingSource<Int, TvShowEntity>

    @Query("UPDATE tvshow SET isFavorite = :isFavorite WHERE id = :tvShowId")
    suspend fun setIsFavorite(tvShowId: String, isFavorite: Boolean)

    @Query("DELETE FROM tvshow WHERE category = :category")
    suspend fun clear(category: String)

    @Query("SELECT COUNT(id) FROM tvshow WHERE category = :category ")
    suspend fun existData(category: String): Int
}