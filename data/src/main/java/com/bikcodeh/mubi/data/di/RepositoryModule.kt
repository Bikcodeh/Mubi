package com.bikcodeh.mubi.data.di

import androidx.paging.ExperimentalPagingApi
import com.bikcodeh.mubi.data.local.TvShowDatabase
import com.bikcodeh.mubi.data.local.dao.TvShowDao
import com.bikcodeh.mubi.data.mappers.TvShowMapper
import com.bikcodeh.mubi.data.remote.service.TVApi
import com.bikcodeh.mubi.data.repository.TvRepositoryImpl
import com.bikcodeh.mubi.domain.repository.TvRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesTvRepository(
        tvShowDao: TvShowDao,
        tvShowDatabase: TvShowDatabase,
        tvApi: TVApi,
        tvShowMapper: TvShowMapper
    ): TvRepository =
        TvRepositoryImpl(
            tvShowDao,
            tvShowDatabase,
            tvApi,
            tvShowMapper
        )
}