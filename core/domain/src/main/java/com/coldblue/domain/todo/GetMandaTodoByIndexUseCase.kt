package com.coldblue.domain.todo

import com.coldblue.data.repository.todo.MandaTodoRepository
import com.coldblue.model.MandaTodo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMandaTodoByIndexUseCase @Inject constructor(
    private val mandaTodoRepository: MandaTodoRepository
) {
    operator fun invoke(): Flow<List<MandaTodo>> =
        mandaTodoRepository.getMandaTodo()
}