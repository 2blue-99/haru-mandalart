package com.coldblue.mandalart.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import com.colddelight.mandalart.R


@Composable
fun getTagList(): List<String> =
    stringArrayResource(id = R.array.tags).toList()
