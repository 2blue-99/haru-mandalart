package com.coldblue.data.repo

import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow

interface MandaDetailRepository {
    fun getMandaDetails(): Flow<List<MandaDetail>>
    suspend fun upsertMandaDetails(mandaDetails: List<MandaDetail>)
    suspend fun deleteMandaDetail(idList: List<Int>)
    suspend fun deleteAllMandaDetail()
}