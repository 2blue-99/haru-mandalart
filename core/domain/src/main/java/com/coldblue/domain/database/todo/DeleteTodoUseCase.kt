package com.coldblue.domain.database.todo

import com.coldblue.data.repo.TodoRepo
import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepo: TodoRepo
){
    suspend operator fun invoke() =
        todoRepo.deleteTodoRepo()
}