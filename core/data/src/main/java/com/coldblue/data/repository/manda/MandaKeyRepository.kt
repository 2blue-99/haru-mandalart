package com.coldblue.data.repository.manda

import com.coldblue.data.sync.Syncable
import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow

interface MandaKeyRepository: Syncable {
    fun getMandaKeys(): Flow<List<MandaKey>>
    suspend fun upsertMandaKeys(mandaKeys: List<MandaKey>)
    suspend fun deleteMandaKeys(keyIdList: List<Int>, detailIdList: List<Int>)
    suspend fun deleteAllMandaDetail()

    fun isInit():Flow<Boolean>

}