package com.coldblue.domain.todo

import com.coldblue.data.repository.todo.TodoRepository
import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<Todo>> {
        return todoRepository.getTodo(date).map {
            it.sortedWith(compareBy({ it.time }))
        }
    }
}