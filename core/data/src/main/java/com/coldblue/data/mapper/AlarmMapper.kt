package com.coldblue.data.mapper

import com.coldblue.database.entity.NotificationEntity
import com.coldblue.model.NotificationAlarmItem

object AlarmMapper {

    fun NotificationAlarmItem.asEntity(): NotificationEntity =
        NotificationEntity(this.time!!, this.title!!, this.id)

    fun NotificationEntity.asDomain(): NotificationAlarmItem =
        NotificationAlarmItem(this.time, this.title, this.id)

    fun List<NotificationEntity>.asDomain(): List<NotificationAlarmItem> =
        this.map { it.asDomain() }
}