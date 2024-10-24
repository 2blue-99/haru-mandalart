package com.coldblue.model

import java.time.LocalDate
import java.time.LocalTime

data class MandaTodo(
    val title: String,
    val isDone: Boolean = false,
    val isAlarm: Boolean = false,
    val time: LocalTime? = null,
    val date: LocalDate = LocalDate.now(),
    val mandaIndex: Int,
    val repeatCycle:Int,
    val isDel: Boolean = false,
    val originId: Int = 0,
    val id: Int = 0,
)