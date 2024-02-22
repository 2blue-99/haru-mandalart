package com.coldblue.haru_mandalart.navigation

import com.coldblue.designsystem.icon.HMIcon

enum class HMDestination(
    val titleTextId: String,
    val selectedIcon: Int = 0,
    val unSelectedIcon: Int = 0
) {
    TUTORIAL(titleTextId = "Tutorial",),
    SETTING(titleTextId = "Setting"),
    LOGIN(titleTextId = "Login"),

    HISTORY(
        titleTextId = "History",
        selectedIcon = HMIcon.selected,
        unSelectedIcon = HMIcon.unSelected
    ),
    MANDA(
        titleTextId = "Manda",
        selectedIcon = HMIcon.selected,
        unSelectedIcon = HMIcon.unSelected
    ),
    TODO(
        titleTextId = "Todo",
        selectedIcon = HMIcon.selected,
        unSelectedIcon = HMIcon.unSelected
    )
}