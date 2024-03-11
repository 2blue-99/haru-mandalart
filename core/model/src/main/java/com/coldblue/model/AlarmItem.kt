package com.coldblue.model

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime?=null,
    val title: String?=null,
    val id: Int
)