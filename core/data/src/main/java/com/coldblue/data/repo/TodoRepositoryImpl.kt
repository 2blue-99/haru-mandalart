package com.coldblue.data.repo

import android.util.Log
import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.mapper.asDomain
import com.coldblue.data.mapper.asEntity
import com.coldblue.data.util.isNotToday
import com.coldblue.data.util.toFirstLocalDate
import com.coldblue.data.util.toLastLocalDate
import com.coldblue.database.dao.TodoDao
import com.coldblue.model.AlarmItem
import com.coldblue.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val alarmScheduler: AlarmScheduler,
) : TodoRepository {

    override suspend fun upsertTodo(todo: Todo) {
        todoDao.upsertTodo(todo.asEntity())
        todo.syncAlarm()
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

    private fun scheduleAlarm(item: AlarmItem) {
        alarmScheduler.schedule(item)
    }

    private fun cancelAlarm(item: AlarmItem) {
        alarmScheduler.cancel(item)
    }

    private fun Todo.syncAlarm() {
        if (time == null) {
            cancelAlarm(AlarmItem(id = id))
            return
        }
        if (date.isNotToday()) return
        if (isDel or isDone) {
            cancelAlarm(AlarmItem(LocalDateTime.of(date, time), title, id))
            return
        }
        scheduleAlarm(AlarmItem(LocalDateTime.of(date, time), title, id))
    }

}