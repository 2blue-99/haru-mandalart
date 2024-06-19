package com.coldblue.data.repository.todo

import com.coldblue.data.sync.Syncable
import com.coldblue.model.MandaTodo
import com.coldblue.model.TodoGraph
import kotlinx.coroutines.flow.Flow

interface MandaTodoRepository: Syncable {
    fun getMandaTodo(): Flow<List<MandaTodo>>
    suspend fun getMandaTodoGraph(): List<TodoGraph>
    fun getDoneDateByIndexYear(index: Int, year: String): Flow<List<String>>
    fun getMandaTodoByIndexDate(index: Int, year: String): Flow<List<MandaTodo>>
    fun getUniqueTodoYear(): Flow<List<String>>

    suspend fun upsertMandaTodo(mandaTodo: MandaTodo)
    suspend fun deleteAllMandaTodo()
}