package com.coldblue.domain.todo

import com.coldblue.data.repo.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoYearRangeUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<List<Int>> =
        todoRepository.getTodoYearList()
}