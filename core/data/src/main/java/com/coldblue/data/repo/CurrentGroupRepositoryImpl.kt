package com.coldblue.data.repo

import com.coldblue.data.mapper.asDomain
import com.coldblue.data.mapper.asEntity
import com.coldblue.database.dao.CurrentGroupDao
import com.coldblue.model.CurrentGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrentGroupRepositoryImpl @Inject constructor(
    private val currentGroupDao: CurrentGroupDao,
) : CurrentGroupRepository {
    override suspend fun upsertCurrentGroup(currentGroup: CurrentGroup) {
        currentGroupDao.upsertCurrentGroup(currentGroup.asEntity())
    }

    override fun getCurrentGroup(): Flow<List<CurrentGroup>> {
        return currentGroupDao.getCurrentGroup().map { it.asDomain() }
    }

    override suspend fun delCurrentGroup(currentGroupId: Int) {
        currentGroupDao.deleteCurrentGroup(currentGroupId)
    }


}