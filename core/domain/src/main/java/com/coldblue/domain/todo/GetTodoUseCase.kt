package com.coldblue.domain.todo

import com.coldblue.data.repo.TodoRepo
import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class GetTodoUseCase @Inject constructor(
    private val todoRepo: TodoRepo
) {
    operator fun invoke(): Flow<List<Todo>> =
        todoRepo.getTodoRepo()
}