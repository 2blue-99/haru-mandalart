package com.coldblue.data.repo

import com.coldblue.database.dao.TodoDao
import kotlinx.coroutines.flow.Flow

interface TodoRepo {
    fun getTodoRepo(): Flow<List<TodoDao>>
    suspend fun upsertTodoRepo(todoEntities: List<TodoDao>)
    suspend fun deleteTodoRepo()
}