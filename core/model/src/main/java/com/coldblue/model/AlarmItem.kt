package com.coldblue.model

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val title: String,
    val id: Int
)