package com.coldblue.network

import com.coldblue.network.model.MandaModel
import com.coldblue.network.model.TodoModel
import io.github.jan.supabase.compose.auth.ComposeAuth

interface SupabaseDataSource {
    val clientToken: String?
    val composeAuth: ComposeAuth

    suspend fun upsertTodoData(data: List<TodoModel>): List<Int>
    suspend fun insertMandalartData(data: MandaModel)
    suspend fun readUpdatedData(date: String): List<TodoModel>
    suspend fun deleteMandalartData(id: Int)
}