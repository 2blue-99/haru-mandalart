package com.coldblue.domain.todo

import com.coldblue.data.repo.TodoRepository
import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetTodoYearRangeUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<Pair<LocalDate,LocalDate>> =
        todoRepository.getTodoYearRange()
}