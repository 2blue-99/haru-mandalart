package com.coldblue.data.repo

import com.coldblue.database.entity.MandaEntity
import kotlinx.coroutines.flow.Flow

interface MandaRepo {
    fun getMandaRepo(): Flow<List<MandaEntity>>
    suspend fun upsertMandaRepo(mandaEntities: List<MandaEntity>)
    suspend fun deleteMandaRepo()
}