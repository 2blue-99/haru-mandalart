package com.coldblue.data.mapper

import com.coldblue.data.util.getUpdateTime
import com.coldblue.data.util.toDate
import com.coldblue.data.util.toTime
import com.coldblue.database.entity.MandaTodoEntity
import com.coldblue.model.MandaTodo
import com.coldblue.network.model.NetworkMandaTodo

object MandaTodoMapper {
    fun List<MandaTodoEntity>.asDomain(): List<MandaTodo> {
        return this.map { it.asDomain() }
    }

    fun MandaTodoEntity.asDomain(): MandaTodo {
        return MandaTodo(
            title,
            isDone,
            isAlarm,
            time,
            date,
            mandaIndex,
            repeatCycle,
            isDel,
            originId,
            id
        )
    }

    fun List<NetworkMandaTodo>.asEntity(mandaTodoIds: List<Int?>): List<MandaTodoEntity> {
        return this.mapIndexed { index, mandaTodo ->
            MandaTodoEntity(
                originId = mandaTodo.id,
                isSync = true,
                isDel = mandaTodo.is_del,
                updateTime = mandaTodo.update_time,
                mandaIndex = mandaTodo.manda_index,
                isDone = mandaTodo.is_done,
                title = mandaTodo.title,
                time = mandaTodo.time.toTime(),
                date = mandaTodo.date.toDate(),
                repeatCycle = mandaTodo.repeat_cycle,
                isAlarm = mandaTodo.is_alarm,
                id = mandaTodoIds[index] ?: 0,
            )
        }
    }

    fun List<MandaTodoEntity>.asNetworkModel(): List<NetworkMandaTodo> {
        return this.map {
            NetworkMandaTodo(
                manda_index = it.mandaIndex,
                title = it.title,
                is_done = it.isDone,
                update_time = it.updateTime,
                is_alarm = it.isAlarm,
                time = if (it.time == null) null else it.time.toString(),
                date = it.date.toString(),
                repeat_cycle = it.repeatCycle,
                is_del = it.isDel,
                id = it.originId
            )
        }
    }

    fun List<MandaTodoEntity>.asSyncedEntity(originIds: List<Int>): List<MandaTodoEntity> {
        return this.mapIndexed { index, entity ->
            MandaTodoEntity(
                originId = originIds[index],
                isSync = true,
                isDel = entity.isDel,
                updateTime = entity.updateTime,
                mandaIndex = entity.mandaIndex,
                isDone = entity.isDone,
                title = entity.title,
                time = entity.time,
                date = entity.date,
                repeatCycle = entity.repeatCycle,
                isAlarm = entity.isAlarm,
                id = entity.id,
            )
        }
    }

    fun MandaTodo.asEntity(): MandaTodoEntity {
        return MandaTodoEntity(
            title,
            mandaIndex,
            isDone,
            isAlarm,
            time,
            date,
            repeatCycle,
            originId,
            isSync = false,
            isDel,
            getUpdateTime(),
            id
        )
    }
}