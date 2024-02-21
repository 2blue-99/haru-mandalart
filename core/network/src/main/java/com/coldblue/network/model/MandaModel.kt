package com.coldblue.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MandaModel(
    val id: Int,
    val cnt: Int
)