package com.coldblue.domain.todo

import com.coldblue.data.repo.TodoRepository
import com.coldblue.model.Todo
import javax.inject.Inject

class UpsertTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo) = todoRepository.upsertTodo(todo)
}