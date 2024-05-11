package com.coldblue.data.mapper

import com.coldblue.data.util.getUpdateTime
import com.coldblue.database.entity.MandaTodoEntity
import com.coldblue.model.MandaTodo

object MandaTodoMapper {
    fun List<MandaTodoEntity>.asDomain(): List<MandaTodo> {
        return this.map { it.asDomain() }
    }

    fun MandaTodoEntity.asDomain(): MandaTodo {
        return MandaTodo(title, isDone, isAlarm, time, date, mandaIndex, isDel, originId, id)
    }

    fun MandaTodo.asEntity(): MandaTodoEntity {
        return MandaTodoEntity(
            title,
            mandaIndex,
            isDone,
            isAlarm,
            time,
            date,
            originId,
            isSync = false,
            isDel,
            getUpdateTime(),
            id
        )
    }
}