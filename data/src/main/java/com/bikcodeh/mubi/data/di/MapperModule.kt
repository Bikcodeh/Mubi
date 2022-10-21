package com.bikcodeh.mubi.data.di

import com.bikcodeh.mubi.data.mappers.TvShowMapper
import com.bikcodeh.mubi.data.mappers.TvShowMapperEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MapperModule {

    @Provides
    @ViewModelScoped
    fun providesTvShowMapper(): TvShowMapper = TvShowMapper()

    @Provides
    @ViewModelScoped
    fun providesTvShowMapperEntity(): TvShowMapperEntity = TvShowMapperEntity()
}