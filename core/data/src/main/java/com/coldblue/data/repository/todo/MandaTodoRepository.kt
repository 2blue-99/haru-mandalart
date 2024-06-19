package com.coldblue.data.repository.todo

import com.coldblue.data.sync.Syncable
import com.coldblue.model.MandaTodo
import com.coldblue.model.TodoGraph
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MandaTodoRepository: Syncable {
    fun getMandaTodo(): Flow<List<MandaTodo>>
    suspend fun getMandaTodoGraph(): List<TodoGraph>
    fun getMandaTodoByIndexYear(index: Int, year: String): Flow<List<MandaTodo>>
    fun getUniqueTodoYear(): Flow<List<String>>

    suspend fun upsertMandaTodo(mandaTodo: MandaTodo)
    suspend fun deleteAllMandaTodo()
}