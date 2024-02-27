package com.coldblue.data.repo

import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow

interface MandaKeyRepository {
    fun getMandaKeys(): Flow<List<MandaKey>>
    suspend fun upsertMandaKeys(mandaKeys: List<MandaKey>)
}