package com.coldblue.data.repo

import com.coldblue.database.dao.TodoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepoImpl @Inject constructor(
    private val todoDao: TodoDao
): TodoRepo {
    override fun getTodoRepo(): Flow<List<TodoDao>> =
        todoDao.getTodoEntities()
    override suspend fun upsertTodoRepo(todoEntities: List<TodoDao>) {
        todoDao.upsertTodoEntities(todoEntities)
    }

    override suspend fun deleteTodoRepo() {
        todoDao.deleteTodoEntities()
    }
}