package com.coldblue.data.repository.todo

import com.coldblue.model.TodoGroup
import kotlinx.coroutines.flow.Flow

interface TodoGroupRepository {
    suspend fun upsertTodoGroup(todoGroup: TodoGroup)

    fun getTodoGroup(): Flow<List<TodoGroup>>

    suspend fun delTodoGroup(todoGroupId: Int)

}