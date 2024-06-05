package com.coldblue.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkMandaTodo(
    val id: Int = 0,
    val manda_index: Int,
    val title: String,
    val is_del: Boolean,
    val is_done: Boolean,
    val is_alarm: Boolean,
    val date: String,
    val update_time: String,
    val time: String? = null,
    val user_id: String? = null,
)
