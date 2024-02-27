package com.coldblue.model

import java.time.LocalDate

data class TodoGroup(
    val name: String,
    val isSync: Boolean = false,
    val isDel: Boolean = false,
    val updateTime: String = LocalDate.now().toString(),
    val originId: Int = 0,
    val id: Int = 0
)