package com.coldblue.model

import java.time.LocalDate

data class Todo(
    val title: String,
    val content: String,
    val isDone: Boolean=false,
    val time: String="",
    val date: LocalDate=LocalDate.now(),
    val isSync: Boolean = false,
    val isDel: Boolean = false,
    val updateTime: String = LocalDate.now().toString(),
    val originId: Int = 0,
    val todoGroupId: Int? = null,
    val groupName: String = "",
    val id: Int = 0,
)
