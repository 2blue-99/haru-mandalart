package com.coldblue.data.repo

import com.coldblue.data.mapper.asDomain
import com.coldblue.data.mapper.asEntity
import com.coldblue.database.dao.TodoDao
import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
) : TodoRepository {

    override suspend fun upsertTodo(todo: Todo) {
        todoDao.upsertTodo(todo.asEntity())
    }

    override fun getTodo(date: LocalDate): Flow<List<Todo>> {
        return todoDao.getTodo(date).map { it.asDomain() }
    }

    override suspend fun delTodo(todoId: Int) {
        todoDao.deleteTodo(todoId)
    }
}