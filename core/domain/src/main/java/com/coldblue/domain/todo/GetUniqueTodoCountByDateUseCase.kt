package com.coldblue.domain.todo

import com.coldblue.data.repository.todo.TodoRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetUniqueTodoCountByDateUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<Int> =
        todoRepository.getUniqueTodoCountByDate()
}