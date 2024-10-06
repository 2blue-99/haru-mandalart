package com.coldblue.model

import java.time.LocalDateTime

data class NotificationAlarmItem(
    val time: LocalDateTime?=null,
    val title: String?=null,
    val id: Int
)