package com.coldblue.data.repository.todo

import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TodoRepository {
    suspend fun upsertTodo(todo: Todo)

    fun getTodo(date: LocalDate): Flow<List<Todo>>

    fun getYearlyExistTodoDate(year: Int): Flow<List<LocalDate>>

    fun getTodoYearList(): Flow<List<Int>>

    suspend fun delTodo(todoId: Int)

    suspend fun syncRead():Boolean
    suspend fun syncWrite():Boolean


}