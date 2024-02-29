package com.coldblue.mandalart.model

import androidx.compose.ui.graphics.Color
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey

data class MandaUI(
    val name: String = "",
    var darkColor: Color = HMColor.Dark.Pink,
    var lightColor: Color = HMColor.Light.Pink,
    val id: Int
)

fun MandaDetail.asMandaUI(colors: Pair<Color,Color>): MandaUI =
    MandaUI(
        name = this.name,
        darkColor = colors.first,
        lightColor = colors.second,
        id = this.id
    )

fun MandaKey.asMandaUI(colors: Pair<Color,Color>): MandaUI =
    MandaUI(
        name = this.name,
        darkColor = colors.first,
        lightColor = colors.second,
        id = this.id
    )



//fun MandaDetail.asMandaUI(): MandaUI = MandaUI(
//    name = this.name,
//    mandaId = this.mandaId,
//    id = this.id
//)
//
//fun MandaKey.asMandaUI(): MandaUI = MandaUI(
//    name = this.name,
//    colorIndex = this.colorIndex,
//    id = this.id
//)