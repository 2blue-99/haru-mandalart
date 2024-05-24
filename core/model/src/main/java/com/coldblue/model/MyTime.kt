package com.coldblue.model

import java.time.LocalTime

data class MyTime(
    val hour: Int,
    val minute: Int,
    val displayText: String,
    val time: LocalTime
)