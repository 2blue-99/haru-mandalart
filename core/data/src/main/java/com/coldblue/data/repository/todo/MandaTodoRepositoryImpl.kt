package com.coldblue.data.repository.todo

import com.coldblue.data.alarm.AlarmScheduler
import com.coldblue.data.mapper.MandaTodoMapper.asDomain
import com.coldblue.data.mapper.MandaTodoMapper.asEntity
import com.coldblue.data.mapper.MandaTodoMapper.asNetworkModel
import com.coldblue.data.mapper.MandaTodoMapper.asSyncedEntity
import com.coldblue.data.sync.SyncHelper
import com.coldblue.data.util.getUpdateTime
import com.coldblue.data.util.isPassed
import com.coldblue.database.dao.MandaKeyDao
import com.coldblue.database.dao.MandaTodoDao
import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.model.AlarmItem
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
) : MandaTodoRepository {
    override fun getMandaTodo(): Flow<List<MandaTodo>> {
        return mandaTodoDao.getMandaTodo().map { it.asDomain() }
    }

    override suspend fun getAllMandaTodoGraph(): List<Pair<Int, Int>> {
        // MandaKeyEntity 모양으로 옴
        val name = mandaKeyDao.getMandaKeys().first()
        // count에는 핵심 목표 포함 0~8
        val count = mandaTodoDao.getAllMandaTodoCount()
        Logger.d(name)
        Logger.d(count)
        return listOf(Pair(1,1))
    }

    override fun getMandaTodoByIndex(index: Int): Flow<List<MandaTodo>> {
        return mandaTodoDao.getMandaTodoByIndex(index).map { it.asDomain() }
    }


    override suspend fun upsertMandaTodo(mandaTodo: MandaTodo) {
        mandaTodoDao.upsertMandaTodo(mandaTodo.asEntity())
        mandaTodo.syncAlarm()
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
