package com.coldblue.tutorial

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource

object TutorialUtil {
    @Composable
    fun getTextList(): List<String> =
        stringArrayResource(id = R.array.explain).toList()

    fun getImageList(): List<Int> =
        listOf(
            R.drawable.tutorial_first,
            R.drawable.tutorial_second,
            R.drawable.tutorial_third_top,
            R.drawable.tutorial_fourth,
            )
}