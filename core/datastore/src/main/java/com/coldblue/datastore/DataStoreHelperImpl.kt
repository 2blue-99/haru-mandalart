package com.coldblue.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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
    private val tutorialKey = booleanPreferencesKey("tutorial")
    private val alarmKey = booleanPreferencesKey("alarm")

    val userToken: Flow<String> = dataStore.data.map { preferences -> preferences[authKey] ?: "" }

    val todoUpdateTime: Flow<String> = dataStore.data.map { preferences -> preferences[todoKey] ?: "" }

    val mandaUpdateTime: Flow<String> = dataStore.data.map { preferences -> preferences[mandaKey] ?: "" }

    val isTutorial: Flow<Boolean> = dataStore.data.map { preferences -> preferences[tutorialKey] ?: false }

    val isAlarm: Flow<Boolean> = dataStore.data.map { preferences -> preferences[alarmKey] ?: false }


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

    override suspend fun updateTutorial(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[tutorialKey] = state
        }
    }

    override suspend fun updateAlarm(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[alarmKey] = state
        }
    }
}