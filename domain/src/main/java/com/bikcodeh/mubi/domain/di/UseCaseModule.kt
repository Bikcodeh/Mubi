package com.bikcodeh.mubi.domain.di

import com.bikcodeh.mubi.domain.repository.TvRepository
import com.bikcodeh.mubi.domain.usecase.GetTvShowsUC
import com.bikcodeh.mubi.domain.usecase.SearchTvShowsUC
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

}