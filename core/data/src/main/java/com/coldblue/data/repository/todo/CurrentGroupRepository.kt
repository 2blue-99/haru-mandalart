package com.coldblue.data.repository.todo

import com.coldblue.data.sync.Syncable
import com.coldblue.model.CurrentGroup
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CurrentGroupRepository : Syncable {
    suspend fun upsertCurrentGroup(currentGroup: CurrentGroup)

    suspend fun getCurrentGroup(date: LocalDate): Flow<List<CurrentGroup>>

    suspend fun delCurrentGroup(currentGroupId: Int, todoGroupId: Int,date: LocalDate)
}