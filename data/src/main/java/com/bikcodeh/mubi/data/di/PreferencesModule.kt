package com.bikcodeh.mubi.data.di

import android.content.Context
import com.bikcodeh.mubi.data.local.preferences.LoginDataStoreOperationImpl
import com.bikcodeh.mubi.domain.repository.LoginDataStoreOperations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PreferencesModule {

    @Provides
    @ViewModelScoped
    fun providesLoginDataStoreOperations(
        @ApplicationContext context: Context
    ): LoginDataStoreOperations = LoginDataStoreOperationImpl(
        context
    )
}