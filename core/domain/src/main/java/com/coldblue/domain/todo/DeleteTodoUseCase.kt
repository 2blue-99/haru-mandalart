package com.coldblue.domain.todo

import com.coldblue.data.repository.TodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todoId: Int) = todoRepository.delTodo(todoId)
}