package com.coldblue.network.datasource

import com.coldblue.model.MandaDetail
import com.coldblue.network.model.NetworkMandaDetail

interface MandaDetailDataSource {

    suspend fun getMandaDetail(update: String): List<NetworkMandaDetail>

    suspend fun upsertMandaDetail(mandaDetails: List<NetworkMandaDetail>): List<Int>
}