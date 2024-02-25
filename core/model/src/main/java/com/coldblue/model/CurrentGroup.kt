package com.coldblue.model

data class CurrentGroup(
    val todoGroupId: Int,
    val name: String,
    val isSync: Boolean,
    val isDel: Boolean,
    val updateTime: String,
    val id: Int = 0,
)