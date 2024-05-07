package com.coldblue.haru_mandalart.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.Manda
import com.coldblue.mandalart.navigation.mandaRoute


enum class TopLevelDestination(
    val route: String,
    val titleText: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
) {

//    HISTORY(
//        route= historyRoute,
//        titleText = "기록",
//        selectedIcon = IconPack.History,
//        unSelectedIcon = IconPack.History
//    ),
    MANDA(
        route= mandaRoute,
        titleText = "만다라트",
        selectedIcon = IconPack.Manda,
        unSelectedIcon = IconPack.Manda
    ),
//    TODO(
//        route= todoRoute,
//        titleText = "홈",
//        selectedIcon = IconPack.Home,
//        unSelectedIcon = IconPack.Home
//    )
}