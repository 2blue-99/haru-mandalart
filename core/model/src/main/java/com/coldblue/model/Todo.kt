package com.coldblue.model

import java.time.LocalDate

data class Todo(
    val title: String,
    val content: String,
    val isDone: Boolean=false,
    val time: String="",
    val date: LocalDate=LocalDate.now(),
    val todoGroupId: Int=-1,
    val groupName: String = "",
    val isDel: Boolean = false,
    val originId: Int = 0,
    val id: Int = 0,
)
