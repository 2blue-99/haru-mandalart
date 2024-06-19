package com.coldblue.domain.todo

import com.coldblue.data.repository.todo.MandaTodoRepository
import com.coldblue.model.TodoGraph
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMandaTodoGraphUseCase @Inject constructor(
    private val mandaTodoRepository: MandaTodoRepository
) {
    operator fun invoke(): Flow<List<TodoGraph>> = flow {
        emit(mandaTodoRepository.getMandaTodoGraph())
    }
}