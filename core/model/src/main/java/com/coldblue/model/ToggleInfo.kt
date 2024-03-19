package com.coldblue.model


data class ToggleInfo(
    val isChecked: Boolean,
    val text: String,
    val groupId: Int? = null,
    val originId: Int = 0,
    val plus: Long = 0
)

