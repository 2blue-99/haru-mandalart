package com.coldblue.model


data class MandaDetail(
    val name: String,
    val mandaId: Int,
    val isDone: Boolean,
    val id: Int = 0
)

data class MandaKey(
    val name: String,
    val colorIndex: Int = 0,
    val id: Int = 0
)

data class MandaUI(
    val name: String = "",
    val mandaId: Int? = null,
    val colorIndex: Int? = null,
    val id: Int
)

fun MandaDetail.asMandaUI(): MandaUI = MandaUI(
        name = this.name,
        mandaId = this.mandaId,
        id = this.id
    )

fun MandaKey.asMandaUI(): MandaUI = MandaUI(
    name = this.name,
    colorIndex = this.colorIndex,
    id = this.id
)