package com.bikcodeh.mubi.data.di

import com.bikcodeh.mubi.data.mappers.SeasonMapper
import com.bikcodeh.mubi.data.mappers.SeasonMapperEntity
import com.bikcodeh.mubi.data.mappers.TvShowMapper
import com.bikcodeh.mubi.data.mappers.TvShowMapperEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun providesSeasonMapperEntity(): SeasonMapperEntity = SeasonMapperEntity()

    @Provides
    @Singleton
    fun providesSeasonMapper(): SeasonMapper = SeasonMapper()

    @Provides
    @Singleton
    fun providesTvShowMapper(
        seasonMapper: SeasonMapper
    ): TvShowMapper = TvShowMapper(seasonMapper)

    @Provides
    @Singleton
    fun providesTvShowMapperEntity(
        seasonMapperEntity: SeasonMapperEntity
    ): TvShowMapperEntity = TvShowMapperEntity(seasonMapperEntity)
}