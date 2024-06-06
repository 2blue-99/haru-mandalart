package com.coldblue.domain.todo

import com.coldblue.data.repository.todo.MandaTodoRepository
import com.coldblue.model.MandaTodo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMandaTodoCountUseCase @Inject constructor(
    private val mandaTodoRepository: MandaTodoRepository
) {
    suspend operator fun invoke(): List<Pair<Int,Int>> =
        mandaTodoRepository.getAllMandaTodoCount()
}