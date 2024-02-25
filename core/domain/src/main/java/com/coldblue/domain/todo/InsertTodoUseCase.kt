package com.coldblue.domain.todo

import com.coldblue.data.repo.TodoRepo
import com.coldblue.model.Todo
import javax.inject.Inject

class InsertTodoUseCase @Inject constructor(
    private val todoRepo: TodoRepo
){
    suspend operator fun invoke(list:List<Todo>) =
        todoRepo.upsertTodoRepo(list)
}