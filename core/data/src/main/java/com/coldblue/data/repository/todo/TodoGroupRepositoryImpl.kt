package com.coldblue.data.repository.todo

import com.coldblue.data.mapper.TodoGroupMapper.asEntity
import com.coldblue.data.mapper.TodoGroupMapper.asNetworkModel
import com.coldblue.data.mapper.TodoGroupMapper.asSyncedEntity
import com.coldblue.data.mapper.asDomain
import com.coldblue.data.mapper.asEntity
import com.coldblue.data.sync.SyncHelper
import com.coldblue.database.dao.TodoGroupDao
import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.model.TodoGroup
import com.coldblue.network.datasource.TodoGroupDataSource
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoGroupRepositoryImpl @Inject constructor(
    private val todoGroupDao: TodoGroupDao,
    private val todoGroupDataSource: TodoGroupDataSource,
    private val syncHelper: SyncHelper,
    private val updateTimeDataSource: UpdateTimeDataSource,

    ) : TodoGroupRepository {
    override suspend fun upsertTodoGroup(todoGroup: TodoGroup) {
        todoGroupDao.upsertTodoGroup(todoGroup.asEntity())
        syncHelper.syncWrite()
    }

    override suspend fun upsertTodoGroup(todoGroupId: Int, name: String) {
        todoGroupDao.upsertTodoGroup(todoGroupId, name)
        syncHelper.syncWrite()
    }

    override fun getTodoGroup(): Flow<List<TodoGroup>> {
        return todoGroupDao.getTodoGroup().map { it.asDomain() }
    }

    override suspend fun delTodoGroup(todoGroupId: Int) {
        todoGroupDao.deleteTodoGroupAndRelated(todoGroupId)
    }

    override suspend fun syncRead(): Boolean {
        try {
            val remoteNew =
                todoGroupDataSource.getTodoGroup(updateTimeDataSource.todoGroupUpdateTime.first())
            val originIds = remoteNew.map { it.id }
            val todoGroupIds = todoGroupDao.getTodoGroupIdByOriginIds(originIds)
            val toUpsertTodoGroups = remoteNew.asEntity(todoGroupIds)
            todoGroupDao.upsertTodoGroup(toUpsertTodoGroups)
            syncHelper.setMaxUpdateTime(
                toUpsertTodoGroups,
                updateTimeDataSource::setTodoGroupUpdateTime
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
                todoGroupDao.getToWriteTodoGroups(updateTimeDataSource.todoGroupUpdateTime.first())
            val originIds = todoGroupDataSource.upsertTodoGroup(localNew.asNetworkModel())
            val toUpsertTodoGroups = localNew.asSyncedEntity(originIds)
            todoGroupDao.upsertTodoGroup(toUpsertTodoGroups)
            syncHelper.setMaxUpdateTime(toUpsertTodoGroups, updateTimeDataSource::setTodoUpdateTime)
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }
}