package com.coldblue.data.repository.todo

import com.coldblue.data.mapper.asDomain
import com.coldblue.data.mapper.asEntity
import com.coldblue.data.sync.SyncHelper
import com.coldblue.database.dao.TodoGroupDao
import com.coldblue.model.TodoGroup
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoGroupRepositoryImpl @Inject constructor(
    private val todoGroupDao: TodoGroupDao,
    private val syncHelper: SyncHelper,
    ) : TodoGroupRepository {
    override suspend fun upsertTodoGroup(todoGroup: TodoGroup) {
        todoGroupDao.upsertTodoGroup(todoGroup.asEntity())
    }

    override fun getTodoGroup(): Flow<List<TodoGroup>> {
        return todoGroupDao.getTodoGroup().map { it.asDomain() }
    }

    override suspend fun delTodoGroup(todoGroupId: Int) {
        todoGroupDao.deleteTodoGroup(todoGroupId)
    }

    override suspend fun syncRead(): Boolean {
        try {
            val remoteNew = syncHelper.toSyncData(todoDataSource::getTodo)
            val originIds = remoteNew.map { it.id }
            val todoIds = todoDao.getTodoIdByOriginIds(originIds)
            val toUpsertTodos = remoteNew.asEntity(todoIds)
            todoDao.upsertTodos(toUpsertTodos)
            syncHelper.setMaxUpdateTime(toUpsertTodos)
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }

    override suspend fun syncWrite(): Boolean {
        TODO("Not yet implemented")
    }
}