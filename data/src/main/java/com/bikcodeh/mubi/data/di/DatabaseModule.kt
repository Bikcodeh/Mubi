package com.bikcodeh.mubi.data.di

import android.content.Context
import androidx.room.Room
import com.bikcodeh.mubi.data.local.TvShowDatabase
import com.bikcodeh.mubi.data.local.dao.TvShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesTvShowDatabase(@ApplicationContext context: Context): TvShowDatabase =
        Room.databaseBuilder(
            context,
            TvShowDatabase::class.java,
            TvShowDatabase.DB_NAME
        ).build()

    @Provides
    @Singleton
    fun providesTvShowDao(tvShowDatabase: TvShowDatabase): TvShowDao =
        tvShowDatabase.tvShowDao()
}