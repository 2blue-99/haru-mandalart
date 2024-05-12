package com.coldblue.data.repository.todo

import com.coldblue.data.sync.Syncable
import com.coldblue.model.MandaTodo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MandaTodoRepository: Syncable {
    suspend fun upsertMandaTodo(mandaTodo: MandaTodo)

    fun getMandaTodo(): Flow<List<MandaTodo>>
}