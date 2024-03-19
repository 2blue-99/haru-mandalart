package com.coldblue.data.mapper

import com.coldblue.database.entity.AlarmEntity
import com.coldblue.model.AlarmItem

object AlarmMapper {

    fun AlarmItem.asEntity(): AlarmEntity =
        AlarmEntity(this.time!!, this.title!!, this.id)

    fun AlarmEntity.asDomain(): AlarmItem =
        AlarmItem(this.time, this.title, this.id)

    fun List<AlarmEntity>.asDomain(): List<AlarmItem> =
        this.map { it.asDomain() }
}