package com.coldblue.data.repository.todo

import com.coldblue.data.sync.Syncable
import com.coldblue.model.MandaTodo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MandaTodoRepository: Syncable {
    fun getMandaTodo(): Flow<List<MandaTodo>>

    suspend fun getAllMandaTodoGraph(): List<Pair<Int,Int>>

    fun getMandaTodoByIndex(index: Int): Flow<List<MandaTodo>>

    suspend fun upsertMandaTodo(mandaTodo: MandaTodo)
    suspend fun deleteAllMandaTodo()
}