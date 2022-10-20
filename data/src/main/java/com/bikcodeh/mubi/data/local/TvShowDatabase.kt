package com.bikcodeh.mubi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bikcodeh.mubi.data.local.dao.RemoteKeysDao
import com.bikcodeh.mubi.data.local.dao.TvShowDao
import com.bikcodeh.mubi.data.local.entity.RemoteKeysEntity
import com.bikcodeh.mubi.data.local.entity.TvShowEntity

@Database(
    entities = [
        TvShowEntity::class,
        RemoteKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TvShowDatabase : RoomDatabase() {

    abstract fun tvShowDao(): TvShowDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val DB_NAME = "tv_show.db"
    }
}