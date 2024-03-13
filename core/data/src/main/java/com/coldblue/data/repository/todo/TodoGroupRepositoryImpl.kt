package com.coldblue.data.repository.todo

import com.coldblue.data.mapper.asDomain
import com.coldblue.data.mapper.asEntity
import com.coldblue.database.dao.TodoGroupDao
import com.coldblue.model.TodoGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoGroupRepositoryImpl @Inject constructor(
    private val todoGroupDao: TodoGroupDao,
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
}