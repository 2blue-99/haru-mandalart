package com.coldblue.haru_mandalart.navigation

import com.coldblue.designsystem.icon.HMIcon

enum class HMDestination(
    val titleTextId: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int
) {
    HISTORY(
        titleTextId = "History",
        selectedIcon = HMIcon.selected,
        unSelectedIcon = HMIcon.unSelected
    ),
//    LOGIN(

//        titleTextId = "Login",
//        selectedIcon = HMIcon.selected,
//        unSelectedIcon = HMIcon.unSelected
//    ),
    MANDA(
        titleTextId = "Manda",
        selectedIcon = HMIcon.selected,
        unSelectedIcon = HMIcon.unSelected
    ),
//    SETTING(
//        titleTextId = "Setting",
//        selectedIcon = HMIcon.selected,
//        unSelectedIcon = HMIcon.unSelected
//    ),
    TODO(
        titleTextId = "Todo",
        selectedIcon = HMIcon.selected,
        unSelectedIcon = HMIcon.unSelected
    ),
//    TUTORIAL(
//        titleTextId = "Tutorial",
//        selectedIcon = HMIcon.selected,
//        unSelectedIcon = HMIcon.unSelected
//    )
}