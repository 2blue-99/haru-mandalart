package com.coldblue.domain.todo

import com.coldblue.data.repository.todo.MandaTodoRepository
import com.coldblue.model.MandaTodo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUniqueTodoYearUseCase @Inject constructor(
    private val mandaTodoRepository: MandaTodoRepository
) {
    operator fun invoke(index: Int): Flow<List<String>> =
        mandaTodoRepository.getUniqueTodoYear(index)
}