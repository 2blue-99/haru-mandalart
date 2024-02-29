package com.coldblue.model

import java.time.LocalDate

data class CurrentGroup(
    val todoGroupId: Int,
    val name: String="",
    val isDel: Boolean = false,
    val id: Int,
)

