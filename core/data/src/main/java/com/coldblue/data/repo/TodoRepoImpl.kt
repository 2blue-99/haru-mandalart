package com.coldblue.data.repo

import com.coldblue.data.mapper.Mapper.asTodo
import com.coldblue.data.mapper.Mapper.asTodoEntity
import com.coldblue.database.dao.TodoDao
import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepoImpl @Inject constructor(
    private val todoDao: TodoDao
): TodoRepo {
    override fun getTodoRepo(): Flow<List<Todo>> =
        todoDao.getTodoEntities().map { it.map { it.asTodo() } }
    override suspend fun upsertTodoRepo(todoEntities: List<Todo>) {
        todoDao.upsertTodoEntities(todoEntities.map { it.asTodoEntity() })
    }
    override suspend fun deleteTodoRepo() {
        todoDao.deleteTodoEntities()
    }
}