package com.coldblue.network.model

import com.coldblue.model.NetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMandaKey(
    override val id: Int = 0,
    val name: String,
    val color_index: Int,
    val is_del: Boolean,
    val update_time: String,
    val user_id: String? = null,
) : NetworkModel
