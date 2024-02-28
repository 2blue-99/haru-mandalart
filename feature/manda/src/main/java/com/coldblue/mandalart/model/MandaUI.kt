package com.coldblue.mandalart.model

import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey

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