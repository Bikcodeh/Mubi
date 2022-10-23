package com.bikcodeh.mubi.domain.repository

import kotlinx.coroutines.flow.Flow

interface LoginDataStoreOperations {
    suspend fun saveLogin(isLoggedIn: Boolean)
    fun getIsLogged(): Flow<Boolean>
}