package com.bikcodeh.mubi.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bikcodeh.mubi.data.local.db.dao.RemoteKeysDao
import com.bikcodeh.mubi.data.local.db.dao.TvShowDao
import com.bikcodeh.mubi.domain.entity.RemoteKeysEntity
import com.bikcodeh.mubi.domain.entity.TvShowEntity

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