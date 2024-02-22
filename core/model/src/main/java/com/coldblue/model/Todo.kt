package com.coldblue.model

data class Todo(
    val id: Int,
    val originId: Int,
    val harumandaId: Int,
    val isSync: Boolean,
    val isDel: Boolean,
    val updateTime: String,
    val title: String,
    val content: String,
    val time: String
)
