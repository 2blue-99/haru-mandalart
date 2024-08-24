package com.coldblue.tutorial

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource

object HistoryUtil {
    @Composable
    fun getExplainList(): List<String> =
        stringArrayResource(id = R.array.explain).toList()
}