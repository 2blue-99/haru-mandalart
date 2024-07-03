package com.coldblue.data.repository.todo

import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.mapper.MandaTodoMapper.asDomain
import com.coldblue.data.mapper.MandaTodoMapper.asEntity
import com.coldblue.data.mapper.MandaTodoMapper.asNetworkModel
import com.coldblue.data.mapper.MandaTodoMapper.asSyncedEntity
import com.coldblue.data.sync.SyncHelper
import com.coldblue.data.sync.TodoWidgetHelper
import com.coldblue.data.util.getUpdateTime
import com.coldblue.data.util.isPassed
import com.coldblue.database.dao.MandaKeyDao
import com.coldblue.database.dao.MandaTodoDao
import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.model.AlarmItem
import com.coldblue.model.TodoGraph
import com.coldblue.model.MandaTodo
import com.coldblue.network.datasource.MandaTodoDataSource
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class MandaTodoRepositoryImpl @Inject constructor(
    private val mandaTodoDao: MandaTodoDao,
    private val mandaKeyDao: MandaKeyDao,
    private val mandaTodoDataSource: MandaTodoDataSource,
    private val syncHelper: SyncHelper,
    private val updateTimeDataSource: UpdateTimeDataSource,
    private val alarmScheduler: AlarmScheduler,
    private val todoWidgetHelper: TodoWidgetHelper
) : MandaTodoRepository {
    override fun getMandaTodo(): Flow<List<MandaTodo>> {
        return mandaTodoDao.getMandaTodo().map { it.asDomain() }
    }

    /**
     * 작은 목표 값들을 가져와서, 존재하는 작은목표와 부합하는 mandaTodoCount 를 매핑시킴
     * 만약 작은 목표가 최종 목표밖에 없다면 empty list 반환
     * manda key : 1번부터 9번까지 존재
     */
    override suspend fun getMandaTodoGraph(): List<TodoGraph> {

        val result = mutableListOf<TodoGraph>()

        val mandaKeys = mandaKeyDao.getMandaKeys().first().toMutableList()
        if(mandaKeys.none { it.id != 5 }) return emptyList()

        val counts = mandaTodoDao.getAllMandaTodoCount()
        for (i in 1..9) {
            if(i == 5) continue
            val mandaKey = mandaKeys.find { it.id == i }
            result.add(
                if(mandaKeys.isNotEmpty() && mandaKey != null){
                        val todoData = counts[mandaKey.id - 1]
                        TodoGraph(
                            name = mandaKey.name,
                            allCount = todoData.first,
                            doneCount = todoData.second,
                            colorIndex = mandaKey.colorIndex
                        )
                }else{
                    TodoGraph()
                }
            )
        }
        return result
    }

    override fun getTodoExistDateByIndexYear(index: Int, year: String): Flow<List<String>> {
        return mandaTodoDao.getTodoExistDateByIndexYear(index, year)
    }

    override fun getMandaTodoByIndexDate(index: Int, date: String): Flow<List<MandaTodo>> {
        return mandaTodoDao.getMandaTodoByIndexDate(index, date).map { it.asDomain() }
    }

//    override fun getDoneDateByIndexYear(index: Int, year: String): Flow<List<String>> {
//        return mandaTodoDao.getDoneDateByIndexYear(index, year).map {
//            if(it.isNullOrEmpty()) emptyList()
//            else it.asDomain()
//        }
//    }


    override fun getUniqueTodoYear(index: Int): Flow<List<String>> {
        return mandaTodoDao.getUniqueTodoYear(index).map { it ?: listOf() }
    }


    override suspend fun upsertMandaTodo(mandaTodo: MandaTodo) {
        mandaTodoDao.upsertMandaTodo(mandaTodo.asEntity())
        mandaTodo.syncAlarm()
        todoWidgetHelper.widgetUpdate()
        syncHelper.syncWrite()

    }


    override suspend fun deleteAllMandaTodo() {
        mandaTodoDao.deleteAllMandaTodo(getUpdateTime())
        syncHelper.syncWrite()

    }


    override suspend fun syncRead(): Boolean {
        try {
            val remoteNew = mandaTodoDataSource.getTodo(updateTimeDataSource.todoUpdateTime.first())
            val originIds = remoteNew.map { it.id }
            val todoIds = mandaTodoDao.getMandaTodoIdByOriginIds(originIds)
            val toUpsertTodos = remoteNew.asEntity(todoIds)
            mandaTodoDao.upsertMandaTodo(toUpsertTodos)
            syncHelper.setMaxUpdateTime(toUpsertTodos, updateTimeDataSource::setTodoUpdateTime)
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }

    override suspend fun syncWrite(): Boolean {
        try {
            val localNew =
                mandaTodoDao.getToWriteMandaTodos(updateTimeDataSource.todoUpdateTime.first())
            val originIds = mandaTodoDataSource.upsertTodo(localNew.asNetworkModel())

            val toUpsertTodos = localNew.asSyncedEntity(originIds)
            mandaTodoDao.upsertMandaTodo(toUpsertTodos)
            syncHelper.setMaxUpdateTime(toUpsertTodos, updateTimeDataSource::setTodoUpdateTime)
            return true
        } catch (e: Exception) {
            Logger.e("${e.message}")
            return false
        }
    }

    private suspend fun MandaTodo.syncAlarm() {
        // 시간 null 체크
        if (time == null) {
            alarmScheduler.cancel(id)
            return
        }
        // date, time 추가 시점이 과거인지 체크
        if (LocalDateTime.of(date, time).isPassed()) {
            alarmScheduler.cancel(id)
            return
        }
        // 삭제, 완료했는지 체크
        if (isDel or isDone) {
            alarmScheduler.cancel(id)
            return
        }
        alarmScheduler.add(AlarmItem(LocalDateTime.of(date, time), title, id))
    }

}
