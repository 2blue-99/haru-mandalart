package com.coldblue.network.model

import com.coldblue.model.NetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class NetWorkTodoGroup(
    override val id: Int = 0,
    val is_del: Boolean,
    val update_time: String,
    val name: String,
    val user_id: String="",
) : NetworkModel

