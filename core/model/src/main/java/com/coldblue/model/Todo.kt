package com.coldblue.model

import java.time.LocalDate

data class Todo(
    val title: String,
    val content: String,
    val isDone: Boolean,
    val time: String,
    val date: LocalDate,
    val todoGroupId: Int? = null,
    val originId: Int,
    val isSync: Boolean,
    val isDel: Boolean,
    val updateTime: String,
    val id: Int = 0,
)
