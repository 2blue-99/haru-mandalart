package com.coldblue.data.repository.todo

import com.coldblue.data.sync.Syncable
import com.coldblue.model.TodoGroup
import kotlinx.coroutines.flow.Flow

interface TodoGroupRepository: Syncable {
    suspend fun upsertTodoGroup(todoGroup: TodoGroup)
    suspend fun upsertTodoGroup(originGroupId:Int,todoGroupId:Int,name: String)

    fun getTodoGroup(): Flow<List<TodoGroup>>

    suspend fun delTodoGroup(todoGroupId: Int)

}