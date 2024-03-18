package com.coldblue.data.repository.todo

import com.coldblue.data.mapper.CurrentGroupMapper.asDomain
import com.coldblue.data.mapper.CurrentGroupMapper.asEntity
import com.coldblue.data.mapper.CurrentGroupMapper.asNetworkModel
import com.coldblue.data.mapper.CurrentGroupMapper.asSyncedEntity
import com.coldblue.data.mapper.asDomain
import com.coldblue.data.mapper.asEntity
import com.coldblue.data.sync.SyncHelper
import com.coldblue.data.util.getUpdateTime
import com.coldblue.database.dao.CurrentGroupDao
import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.model.CurrentGroup
import com.coldblue.network.datasource.CurrentGroupDataSource
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class CurrentGroupRepositoryImpl @Inject constructor(
    private val currentGroupDao: CurrentGroupDao,
    private val updateTimeDataSource: UpdateTimeDataSource,
    private val currentGroupDataSource: CurrentGroupDataSource,
    private val syncHelper: SyncHelper,
) : CurrentGroupRepository {
    override suspend fun upsertCurrentGroup(currentGroup: CurrentGroup) {
        currentGroupDao.upsertCurrentGroup(currentGroup.asEntity())
        syncHelper.syncWrite()
    }

    override suspend fun getCurrentGroup(date: LocalDate): Flow<List<CurrentGroup>> {
        currentGroupDao.setCurrentGroup(date, getUpdateTime())
        syncHelper.syncWrite()
        return currentGroupDao.getCurrentGroup(date).map { it.asDomain() }
    }

    override suspend fun delCurrentGroup(currentGroupId: Int, todoGroupId: Int, date: LocalDate) {
        currentGroupDao.deleteCurrentGroupWithTodo(
            currentGroupId,
            todoGroupId,
            getUpdateTime(),
            date
        )
        syncHelper.syncWrite()
    }

    override suspend fun syncRead(): Boolean {
        try {
            val remoteNew =
                currentGroupDataSource.getCurrentGroup(updateTimeDataSource.currentGroupUpdateTime.first())
            val originIds = remoteNew.map { it.id }
            val todoGroupIds = currentGroupDao.getCurrentGroupIdByOriginIds(originIds)
            val toUpsertCurrentGroups = remoteNew.asEntity(todoGroupIds)

            currentGroupDao.upsertCurrentGroup(toUpsertCurrentGroups)
            syncHelper.setMaxUpdateTime(
                toUpsertCurrentGroups,
                updateTimeDataSource::setCurrentGroupUpdateTime
            )
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }

    override suspend fun syncWrite(): Boolean {
        try {
            val localNew =
                currentGroupDao.getToWriteCurrentGroup(updateTimeDataSource.currentGroupUpdateTime.first())

            Logger.d(localNew)
            val originIds = currentGroupDataSource.upsertCurrentGroup(localNew.asNetworkModel())
            val toUpsertCurrentGroups = localNew.asSyncedEntity(originIds)
            currentGroupDao.upsertCurrentGroup(toUpsertCurrentGroups)
            syncHelper.setMaxUpdateTime(
                toUpsertCurrentGroups,
                updateTimeDataSource::setCurrentGroupUpdateTime
            )
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }


}