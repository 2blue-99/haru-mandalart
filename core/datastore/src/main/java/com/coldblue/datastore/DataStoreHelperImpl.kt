package com.coldblue.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreHelperImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreHelper {
    private val authKey = stringPreferencesKey("auth")
    private val todoKey = stringPreferencesKey("todo")
    private val mandaKey = stringPreferencesKey("manda")

    override val userToken: Flow<String> =
        dataStore.data.map { preferences -> preferences[authKey] ?: "" }

    override val todoUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[todoKey] ?: "" }

    override val mandaUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[mandaKey] ?: "" }
    override suspend fun updateToken(token: String) {
        dataStore.edit { preferences ->
            preferences[authKey] = token
        }
    }
    override suspend fun updateTodoTime(time: String) {
        dataStore.edit { preferences ->
            preferences[todoKey] = time
        }
    }
    override suspend fun updateMandaTime(time: String) {
        dataStore.edit { preferences ->
            preferences[mandaKey] = time
        }
    }
}