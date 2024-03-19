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
    private val emailKey = stringPreferencesKey("email")
    private val tutorialKey = booleanPreferencesKey("tutorial")
    private val alarmKey = booleanPreferencesKey("alarm")
    private val mandaInitStateKey = booleanPreferencesKey("initManda")
    private val permissionInitStateKey = booleanPreferencesKey("initPermission")

    override val token: Flow<String> =
        dataStore.data.map { preferences -> preferences[tokenKey] ?: "" }
    override val email: Flow<String> =
        dataStore.data.map { preferences -> preferences[emailKey] ?: "" }
    override val isTutorial: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[tutorialKey] ?: false }
    override val isAlarm: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[alarmKey] ?: true }
    override val mandaInitState: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[mandaInitStateKey] ?: false }
    override val permissionInitState: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[permissionInitStateKey] ?: false }

    override suspend fun reset() {
        dataStore.edit { preferences -> preferences[tokenKey] = "" }
        dataStore.edit { preferences -> preferences[tutorialKey] = false }
        dataStore.edit { preferences -> preferences[alarmKey] = false }
        dataStore.edit { preferences -> preferences[emailKey] = "" }
        dataStore.edit { preferences -> preferences[mandaInitStateKey] = false }
    }

    override suspend fun updateToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    override suspend fun updateEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[emailKey] = email
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

    override suspend fun updateMandaInitState(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[mandaInitStateKey] = state
        }
    }

    override suspend fun updatePermissionInitState(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[permissionInitStateKey] = state
        }
    }

}