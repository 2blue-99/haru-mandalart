package com.coldblue.domain.todo

import com.coldblue.data.repository.todo.MandaTodoRepository
import com.coldblue.model.MandaTodo
import com.coldblue.model.Todo
import javax.inject.Inject

class UpsertMandaTodoUseCase @Inject constructor(
    private val mandaTodoRepository: MandaTodoRepository

) {
    suspend operator fun invoke(mandaTodo: MandaTodo) =
        mandaTodoRepository.upsertMandaTodo(mandaTodo)
}