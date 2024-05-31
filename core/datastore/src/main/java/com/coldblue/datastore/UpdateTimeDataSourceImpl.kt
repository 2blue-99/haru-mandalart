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
        val MANDA_KEY = stringPreferencesKey("MANDA_KEY")
        val MANDA_DETAIL = stringPreferencesKey("MANDA_DETAIL")
    }

    override val todoUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[PreferencesKey.TODO] ?: "0" }
    override val mandaKeyUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[PreferencesKey.MANDA_KEY] ?: "0" }
    override val mandaDetailUpdateTime: Flow<String> =
        dataStore.data.map { preferences -> preferences[PreferencesKey.MANDA_DETAIL] ?: "0" }


    override suspend fun reset() {
        val keys = listOf(
            PreferencesKey.TODO,
            PreferencesKey.MANDA_KEY,
            PreferencesKey.MANDA_DETAIL
        )
        keys.forEach {
            dataStore.edit { preferences ->
                preferences[it] = "0"
            }
        }
    }

    override suspend fun setTodoUpdateTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.TODO] = time
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