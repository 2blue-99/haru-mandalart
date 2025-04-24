package com.coldblue.mandalart.state

import androidx.compose.ui.graphics.Color
import com.coldblue.mandalart.model.MandaUI

data class MandaStatus(
    val titleManda: MandaUI,
    val statusColor: Color,
    val donePercentage: Float
)

data class CurrentManda(
    val currentMandaIndex:Int,
    val currentIndex:Int
)
