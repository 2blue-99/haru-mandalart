package com.coldblue.network.datasource

import com.coldblue.network.model.NetworkMandaKey


interface MandaKeyDataSource {

    suspend fun getMandaKey(update: String): List<NetworkMandaKey>

    suspend fun upsertMandaKey(mandaKeys: List<NetworkMandaKey>): List<Int>
}