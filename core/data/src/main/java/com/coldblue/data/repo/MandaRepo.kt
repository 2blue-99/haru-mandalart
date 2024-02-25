package com.coldblue.data.repo

import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow

interface MandaRepo {
    suspend fun getAllMandaRepository(): Flow<MandaDetail>
    suspend fun upsertKeyMandaRepository(mandaKeys: List<MandaKey>)
    suspend fun upsertDetailMandaRepository(mandaDetails: List<MandaDetail>)
}