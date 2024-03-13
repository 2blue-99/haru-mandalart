package com.coldblue.network.model

import com.coldblue.model.NetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTodo(
    override val id: Int = 0,
    val title: String,
    val content: String? = null,
    val is_done: Boolean,
    val date: String,
    val update_time: String,
    val todo_group_id: Int? = null,
    val time: String? = null,
    val is_del: Boolean,
    val user_id: String? = null,
) : NetworkModel
