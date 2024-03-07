package com.coldblue.data.repo

import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TodoRepository {
    suspend fun upsertTodo(todo: Todo)

    fun getTodo(date: LocalDate): Flow<List<Todo>>

    fun getTodoDate(year: String): Flow<List<LocalDate>>

    fun getTodoYearRange(): Flow<Pair<LocalDate, LocalDate>>

    suspend fun delTodo(todoId: Int)

}