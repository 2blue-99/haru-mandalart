package com.coldblue.data.repository.manda

import com.coldblue.data.sync.Syncable
import com.coldblue.model.MandaKey
import com.coldblue.model.UpdateNote
import kotlinx.coroutines.flow.Flow

interface UpdateNoteRepository {
    suspend fun getUpdateNote(): UpdateNote
}