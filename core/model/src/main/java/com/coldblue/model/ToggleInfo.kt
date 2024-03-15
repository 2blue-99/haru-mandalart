package com.coldblue.model


data class ToggleInfo(
    val isChecked: Boolean,
    val text: String,
    val currentGroupId: Int? = null,
    val plus: Long = 0
)

