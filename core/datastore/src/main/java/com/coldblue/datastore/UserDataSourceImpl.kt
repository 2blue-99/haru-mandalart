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
class UserDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserDataSource {
    private val tokenKey = stringPreferencesKey("token")
    private val todoKey = stringPreferencesKey("todo")
    private val mandaKey = stringPreferencesKey("manda")
    private val tutorialKey = booleanPreferencesKey("tutorial")
    private val alarmKey = booleanPreferencesKey("alarm")
    private val initKey = booleanPreferencesKey("initManda")

    override val token: Flow<String> = dataStore.data.map { preferences -> preferences[tokenKey] ?: "" }
    override val todoUpdateTime: Flow<String> = dataStore.data.map { preferences -> preferences[todoKey] ?: "0" }
    override val mandaUpdateTime: Flow<String> = dataStore.data.map { preferences -> preferences[mandaKey] ?: "0" }
    override val isTutorial: Flow<Boolean> = dataStore.data.map { preferences -> preferences[tutorialKey] ?: false }
    override val isAlarm: Flow<Boolean> = dataStore.data.map { preferences -> preferences[alarmKey] ?: false }
    override val isInit: Flow<Boolean> = dataStore.data.map { preferences -> preferences[initKey] ?: false }

    override suspend fun updateToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
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

    override suspend fun updateInit(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[initKey] = state
        }
    }
}