package com.coldblue.data.repo

import com.coldblue.model.CurrentGroup
import kotlinx.coroutines.flow.Flow

interface CurrentGroupRepository {
    suspend fun upsertCurrentGroup(currentGroup: CurrentGroup)

    fun getCurrentGroup(): Flow<List<CurrentGroup>>

    suspend fun delCurrentGroup(currentGroupId: Int, todoGroupId: Int)
}