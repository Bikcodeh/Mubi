package com.bikcodeh.mubi.domain.di

import com.bikcodeh.mubi.domain.repository.TvRepository
import com.bikcodeh.mubi.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun providesGetLatestTvShowUC(tvRepository: TvRepository): GetTvShowsUC =
        GetTvShowsUC(tvRepository)

    @Provides
    @ViewModelScoped
    fun providesSearchTvShowsUC(tvRepository: TvRepository): SearchTvShowsUC =
        SearchTvShowsUC(tvRepository)

    @Provides
    @ViewModelScoped
    fun providesSearchTvShowsRemoteUC(tvRepository: TvRepository): SearchTvShowsRemoteUC =
        SearchTvShowsRemoteUC(tvRepository)

    @Provides
    @ViewModelScoped
    fun providesGetDetailTvShowUC(tvRepository: TvRepository): GetDetailTvShowUC =
        GetDetailTvShowUC(tvRepository)

    @Provides
    @ViewModelScoped
    fun providesSetAsFavoriteUC(tvRepository: TvRepository): SetAsFavoriteUC =
        SetAsFavoriteUC(tvRepository)

    @Provides
    @ViewModelScoped
    fun providesGetTvShowByIdLocalUC(tvRepository: TvRepository): GetTvShowByIdLocalUC =
        GetTvShowByIdLocalUC(tvRepository)

    @Provides
    @ViewModelScoped
    fun providesSaveTvShowUC(tvRepository: TvRepository): UpdateTvShowUC =
        UpdateTvShowUC(tvRepository)

}