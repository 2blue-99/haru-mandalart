package com.coldblue.network.datasource

import com.coldblue.network.model.NetworkCurrentGroup

interface CurrentGroupDataSource {

    suspend fun getCurrentGroup(update: String): List<NetworkCurrentGroup>

    suspend fun upsertCurrentGroup(currentGroups: List<NetworkCurrentGroup>): List<Int>
}