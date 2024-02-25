package com.coldblue.data.repo

import com.coldblue.model.KeyManda
import kotlinx.coroutines.flow.Flow

interface MandaRepo {
    fun getMandaRepo(): Flow<List<KeyManda>>
    suspend fun upsertMandaRepo(keyMandaEntities: List<KeyManda>)
    suspend fun deleteAllMandaRepo(keyMandaEntity: KeyManda)
}