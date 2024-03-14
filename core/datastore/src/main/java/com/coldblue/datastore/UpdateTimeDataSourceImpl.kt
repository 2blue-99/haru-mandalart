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
class UpdateTimeDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UpdateTimeDataSource {
    object PreferencesKey {
        val TODO = stringPreferencesKey("TODO")
        val TODO_GROUP = stringPreferencesKey("TODO_GROUP")
        val CURRENT_GROUP = stringPreferencesKey("CURRENT_GROUP")
        val MANDA_KEY = stringPreferencesKey("MANDA_KEY")
        val MANDA_DETAIL = stringPreferencesKey("MANDA_DETAIL")
    }

    override val todoUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[PreferencesKey.TODO] ?: "0" }
    override val todoGroupUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[PreferencesKey.TODO_GROUP] ?: "0" }
    override val currentGroupUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[PreferencesKey.CURRENT_GROUP] ?: "0" }
    override val mandaKeyUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[PreferencesKey.MANDA_KEY] ?: "0" }
    override val mandaDetailUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[PreferencesKey.MANDA_DETAIL] ?: "0" }



    override suspend fun setTodoUpdateTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.TODO] = time
        }
    }

    override suspend fun setTodoGroupUpdateTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.TODO_GROUP] = time
        }
    }

    override suspend fun setCurrentGroupUpdateTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.CURRENT_GROUP] = time
        }
    }

    override suspend fun setMandaKeyUpdateTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.MANDA_KEY] = time
        }
    }

    override suspend fun setMandaDetailUpdateTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.MANDA_DETAIL] = time
        }
    }


}