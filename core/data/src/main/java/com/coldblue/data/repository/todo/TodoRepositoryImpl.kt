package com.coldblue.data.repository.todo

import android.util.Log
import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.mapper.asDomain
import com.coldblue.data.mapper.asEntity
import com.coldblue.data.mapper.asNetworkModel
import com.coldblue.data.sync.SyncWriteHelper
import com.coldblue.data.util.isNotToday
import com.coldblue.data.util.toFirstLocalDate
import com.coldblue.data.util.toLastLocalDate
import com.coldblue.database.dao.TodoDao
import com.coldblue.datastore.UserDataSource
import com.coldblue.model.AlarmItem
import com.coldblue.model.Todo
import com.coldblue.network.datasource.TodoDataSource
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val alarmScheduler: AlarmScheduler,
    private val userDataSource: UserDataSource,
    private val todoDataSource: TodoDataSource,
    private val syncWriteHelper: SyncWriteHelper,
) : TodoRepository {

    override suspend fun upsertTodo(todo: Todo) {
        todoDao.upsertTodo(todo.asEntity())
        todo.syncAlarm()
        syncWriteHelper.syncWrite()
    }

    override fun getTodo(date: LocalDate): Flow<List<Todo>> {
        return todoDao.getTodo(date).map { it.asDomain() }
    }

    override fun getYearlyExistTodoDate(year: Int): Flow<List<LocalDate>> {
        return todoDao.getYearlyExistTodoDate(year.toFirstLocalDate(), year.toLastLocalDate())
    }

    override fun getTodoYearList(): Flow<List<Int>> {
        return todoDao.getTodoMinYear().combine(todoDao.getTodoMaxYear()) { minYear, maxYear ->
            if (minYear != null && maxYear != null)
                (minYear.year..maxYear.year).toList()
            else
                listOf(LocalDate.now().year)
        }
    }

    override suspend fun delTodo(todoId: Int) {
        todoDao.deleteTodo(todoId)
    }

    override suspend fun syncRead(): Boolean {

        try {

            val newTodos = todoDataSource.getTodo(userDataSource.todoUpdateTime.first())
            val originIds = newTodos.map { it.id }
            val todoIds = todoDao.getTodoIdByOriginIds(originIds)
            val toUpsertTodos = newTodos.asEntity(todoIds)
            todoDao.upsertTodos(toUpsertTodos)

            val maxUpdateTime = toUpsertTodos.maxOfOrNull { it.updateTime }
            maxUpdateTime?.run { userDataSource.setTodoUpdateTime(this) }
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }

    }

    override suspend fun syncWrite(): Boolean {
        return try {
            val toWrite = todoDao.getToWriteTodos(userDataSource.todoUpdateTime.first())
            Logger.d(toWrite)
            val originIdList = todoDataSource.upsertTodo(toWrite.asNetworkModel())
            val todosWithOriginId = toWrite.mapIndexed { index, todoEntity ->
                todoEntity.copy(originId = originIdList[index], isSync = true)
            }
            todoDao.upsertTodos(todosWithOriginId)
            val maxUpdateTime = toWrite.maxOfOrNull { it.updateTime }
            maxUpdateTime?.run { userDataSource.setTodoUpdateTime(this) }
            true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            false
        }
    }


    private fun Todo.syncAlarm() {
        if (time == null) {
            alarmScheduler.cancel(AlarmItem(id = id))
            return
        }
        if (date.isNotToday()) return
        if (isDel or isDone) {
            alarmScheduler.cancel(AlarmItem(LocalDateTime.of(date, time), title, id))
            return
        }
        alarmScheduler.schedule(AlarmItem(LocalDateTime.of(date, time), title, id))
    }

}