package com.bikcodeh.mubi.data.di

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

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesTvRepository(tvApi: TVApi): TvRepository =
        TvRepositoryImpl(tvApi)
}