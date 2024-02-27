package com.coldblue.data.repo

import com.coldblue.model.CurrentGroup
import kotlinx.coroutines.flow.Flow

interface CurrentGroupRepository {
    suspend fun upsertCurrentGroup(currentGroup: CurrentGroup)

    fun getCurrentGroup(): Flow<Map<Int,CurrentGroup>>

    suspend fun delCurrentGroup(currentGroupId: Int)
}