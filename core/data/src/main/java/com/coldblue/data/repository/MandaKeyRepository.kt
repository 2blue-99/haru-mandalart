package com.coldblue.data.repository

import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow

interface MandaKeyRepository {
    fun getMandaKeys(): Flow<List<MandaKey>>
    suspend fun upsertMandaKeys(mandaKeys: List<MandaKey>)
    suspend fun deleteMandaKeys(idList: List<Int>)
    suspend fun deleteAllMandaDetail()

}