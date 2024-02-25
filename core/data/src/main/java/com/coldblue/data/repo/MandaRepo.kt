package com.coldblue.data.repo

import com.coldblue.model.MandaCombine
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow

interface MandaRepo {
    fun getAllMandaRepository(): Flow<MandaCombine>
    suspend fun upsertMandaKeysRepository(mandaKeys: List<MandaKey>)
    suspend fun upsertMandaDetailsRepository(mandaDetails: List<MandaDetail>)
}