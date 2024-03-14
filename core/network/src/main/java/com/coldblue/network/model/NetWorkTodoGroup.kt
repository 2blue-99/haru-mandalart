package com.coldblue.network.model

import com.coldblue.model.NetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class NetWorkTodoGroup(
    override val id: Int = 0,
    val name: String,
    val update_time: String,
    val is_del: Boolean,
    val user_id: String? = null,
) : NetworkModel
