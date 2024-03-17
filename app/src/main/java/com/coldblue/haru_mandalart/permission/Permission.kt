package com.coldblue.haru_mandalart.permission

import android.content.Context
import androidx.compose.runtime.Composable

interface Permission {
    @Composable
    fun GetPermission(context: Context)
}