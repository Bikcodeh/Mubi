package com.bikcodeh.mubi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bikcodeh.mubi.data.local.entity.TvShowEntity

@Database(entities = [TvShowEntity::class], version = 1, exportSchema = false)
abstract class TvShowDatabase : RoomDatabase() {

}