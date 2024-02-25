package com.coldblue.model

data class TodoGroup(
    val name: String,
    val originId: Int,
    val isSync: Boolean,
    val isDel: Boolean,
    val updateTime: String,
    val id: Int = 0
)