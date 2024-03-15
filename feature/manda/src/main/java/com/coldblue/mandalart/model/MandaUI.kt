package com.coldblue.mandalart.model

import androidx.compose.ui.graphics.Color
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey

data class MandaUI(
    val name: String = "",
    var darkColor: Color = HMColor.SubText,
    var lightColor: Color = HMColor.SubText,
    var isDone: Boolean = false,
    val id: Int
)

fun MandaKey.asMandaUI(colors: Pair<Color,Color>, isDone: Boolean): MandaUI =
    MandaUI(
        name = this.name,
        darkColor = colors.first,
        lightColor = colors.second,
        isDone = isDone,
        id = this.id
    )

fun MandaDetail.asMandaUI(colors: Pair<Color,Color>, isDone: Boolean): MandaUI =
    MandaUI(
        name = this.name,
        darkColor = colors.first,
        lightColor = colors.second,
        isDone = isDone,
        id = this.id
    )

fun MandaUI.asMandaKey(name: String, colorIndex: Int): MandaKey =
    MandaKey(
        name = name,
        colorIndex = colorIndex,
        id = id
    )

fun MandaUI.asMandaDetail(name: String, isDone: Boolean, colorIndex: Int): MandaDetail =
    MandaDetail(
        name = name,
        isDone = isDone,
        colorIndex = colorIndex,
        id = id
    )

