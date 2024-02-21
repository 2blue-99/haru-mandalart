package com.coldblue.data.repo

import com.coldblue.model.Manda
import kotlinx.coroutines.flow.Flow

interface MandaRepo {
    fun getMandaRepo(): Flow<List<Manda>>
    suspend fun upsertMandaRepo(mandaEntities: List<Manda>)
    suspend fun deleteAllMandaRepo(mandaEntity: Manda)
}