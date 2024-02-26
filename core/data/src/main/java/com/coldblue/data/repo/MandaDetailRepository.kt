package com.coldblue.data.repo

import com.coldblue.model.MandaDetail
import kotlinx.coroutines.flow.Flow

interface MandaDetailRepository {
    fun getMandaDetails(): Flow<List<MandaDetail>>
    suspend fun upsertMandaDetails(mandaDetails: List<MandaDetail>)
}