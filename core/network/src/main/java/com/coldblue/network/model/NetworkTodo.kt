package com.coldblue.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTodo(
   val id: Int = 0,
    val title: String,
    val content: String? = null,
    val is_done: Boolean,
    val date: String,
    val update_time: String,
    val todo_group_id: Int? = null,
    val orgin_group_id: Int,
    val time: String? = null,
    val is_del: Boolean,
    val user_id: String? = null,
)
