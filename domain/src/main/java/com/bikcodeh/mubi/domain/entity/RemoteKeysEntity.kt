package com.bikcodeh.mubi.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val tvShowId: String,
    val category: String,
    val prevKey: Int?,
    val nextKey: Int?
)