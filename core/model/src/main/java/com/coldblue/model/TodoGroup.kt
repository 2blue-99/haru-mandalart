package com.coldblue.model

import java.time.LocalDate

data class TodoGroup(
    val name: String,
    val isDel: Boolean = false,
    val originId: Int = 0,
    val id: Int = 0
)