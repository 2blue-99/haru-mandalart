package com.coldblue.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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
    private val isStartedKey = booleanPreferencesKey("verifiedUser")
    private val emailKey = stringPreferencesKey("email")
    private val explainKey = booleanPreferencesKey("explain")
    private val alarmKey = booleanPreferencesKey("alarm")
    private val mandaInitStateKey = booleanPreferencesKey("initManda")
    private val noteRequestDateKey = stringPreferencesKey("noteRequestDate")
    private val currentMandaIndexKey = intPreferencesKey("currentMandaIndex")

    override val token: Flow<String> =
        dataStore.data.map { preferences -> preferences[tokenKey] ?: "" }
    override val isStarted: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[isStartedKey] ?: false }
    override val email: Flow<String> =
        dataStore.data.map { preferences -> preferences[emailKey] ?: "비회원" }
    override val isExplain: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[explainKey] ?: false }
    override val isAlarm: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[alarmKey] ?: true }
    override val mandaInitState: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[mandaInitStateKey] ?: false }
    override val noteRequestDate: Flow<String> =
        dataStore.data.map { preferences -> preferences[noteRequestDateKey] ?: "1999-08-31" }

    override val currentMandaIndex: Flow<Int> =
        dataStore.data.map { preferences -> preferences[currentMandaIndexKey] ?: 0 }


    override suspend fun updateToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    override suspend fun updateStarted(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[isStartedKey] = state
        }
    }

    override suspend fun updateEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[emailKey] = email
        }
    }

    override suspend fun updateExplain(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[explainKey] = state
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

    override suspend fun updateNoteRequestDate(date: String) {
        dataStore.edit { preferences ->
            preferences[noteRequestDateKey] = date
        }
    }

    override suspend fun updateCurrentMandaIndex(index: Int) {
        dataStore.edit { preferences ->
            preferences[currentMandaIndexKey] = index
        }
    }



    override suspend fun reset() {
        dataStore.edit { preferences -> preferences[tokenKey] = "" }
        dataStore.edit { preferences -> preferences[isStartedKey] = false }
        dataStore.edit { preferences -> preferences[emailKey] = "비회원" }
        dataStore.edit { preferences -> preferences[alarmKey] = false }
        dataStore.edit { preferences -> preferences[mandaInitStateKey] = false }
        dataStore.edit { preferences -> preferences[currentMandaIndexKey] = 0 }
    }
}