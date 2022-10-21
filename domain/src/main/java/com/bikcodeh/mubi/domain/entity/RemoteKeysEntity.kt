package com.bikcodeh.mubi.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tvShowId: String,
    val prevKey: Int?,
    val nextKey: Int?
)