package com.coldblue.network.datasource

import com.coldblue.network.model.NetWorkTodoGroup

interface TodoGroupDataSource {

    suspend fun getTodoGroup(update: String): List<NetWorkTodoGroup>

    suspend fun upsertTodoGroup(todoGroups: List<NetWorkTodoGroup>): List<Int>
}