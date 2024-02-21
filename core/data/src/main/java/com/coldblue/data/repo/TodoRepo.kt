package com.coldblue.data.repo

import com.coldblue.database.dao.TodoDao
import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepo {
    fun getTodoRepo(): Flow<List<Todo>>
    suspend fun upsertTodoRepo(todoEntities: List<Todo>)
    suspend fun deleteTodoRepo()
}