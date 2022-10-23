package com.bikcodeh.mubi.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.bikcodeh.mubi.data.util.Constants.LOGIN_PREFERENCES_KEY
import com.bikcodeh.mubi.data.util.Constants.PREFERENCES_NAME
import com.bikcodeh.mubi.domain.repository.LoginDataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class LoginDataStoreOperationImpl @Inject constructor(
    context: Context
) : LoginDataStoreOperations {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val isLoggedIn = booleanPreferencesKey(LOGIN_PREFERENCES_KEY)
    }

    override suspend fun saveLogin(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.isLoggedIn] = isLoggedIn
        }
    }

    override fun getIsLogged(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw  exception
            }
        }.map { preferences ->
            val isLoggedIn = preferences[PreferencesKeys.isLoggedIn] ?: false
            isLoggedIn
        }
    }
}