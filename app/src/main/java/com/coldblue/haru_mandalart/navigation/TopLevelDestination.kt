package com.coldblue.haru_mandalart.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.History
import com.coldblue.designsystem.iconpack.Home
import com.coldblue.designsystem.iconpack.Manda


enum class TopLevelDestination(
    val titleText: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
) {

    HISTORY(
        titleText = "History",
        selectedIcon = IconPack.History,
        unSelectedIcon = IconPack.History
    ),
    MANDA(
        titleText = "Manda",
        selectedIcon = IconPack.Manda,
        unSelectedIcon = IconPack.Manda
    ),
    TODO(
        titleText = "Todo",
        selectedIcon = IconPack.Home,
        unSelectedIcon = IconPack.Home
    )
}