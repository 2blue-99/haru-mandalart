package com.coldblue.domain.manda

import com.coldblue.data.repository.manda.MandaKeyRepository
import com.coldblue.data.repository.manda.UpdateNoteRepository
import com.coldblue.model.MandaKey
import com.coldblue.model.UpdateNote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpdateNoteUseCase @Inject constructor(
    private val updateNoteRepository: UpdateNoteRepository
){
    suspend operator fun invoke(): UpdateNote =
        updateNoteRepository.getUpdateNote()
}