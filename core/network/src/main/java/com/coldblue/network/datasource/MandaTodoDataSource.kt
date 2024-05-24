package com.coldblue.network.datasource

import com.coldblue.network.model.NetworkMandaTodo

interface MandaTodoDataSource {
    suspend fun getTodo(update: String): List<NetworkMandaTodo>

    suspend fun upsertTodo(todos: List<NetworkMandaTodo>): List<Int>
}