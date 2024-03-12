package com.coldblue.domain.todo

import com.coldblue.data.repository.TodoRepository
import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<Todo>> = todoRepository.getTodo(date)
}