package com.coldblue.data.repo

import com.coldblue.model.CurrentGroup
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CurrentGroupRepository {
    suspend fun upsertCurrentGroup(currentGroup: CurrentGroup)

    fun getCurrentGroup(date: LocalDate): Flow<List<CurrentGroup>>

    suspend fun delCurrentGroup(currentGroupId: Int, todoGroupId: Int)
}