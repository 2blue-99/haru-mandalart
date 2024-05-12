package com.coldblue.designsystem

import androidx.compose.ui.graphics.vector.ImageVector
import com.coldblue.designsystem.iconpack.Back
import com.coldblue.designsystem.iconpack.Check
import com.coldblue.designsystem.iconpack.todo.Circle
import com.coldblue.designsystem.iconpack.History
import com.coldblue.designsystem.iconpack.Home
import com.coldblue.designsystem.iconpack.Manda
import com.coldblue.designsystem.iconpack.Mandalart
import com.coldblue.designsystem.iconpack.Newzoomin
import com.coldblue.designsystem.iconpack.Newzoomout
import com.coldblue.designsystem.iconpack.Plus
import com.coldblue.designsystem.iconpack.ZoomIn
import com.coldblue.designsystem.iconpack.ZoomOut
import com.coldblue.designsystem.iconpack.todo.AddSquare
import com.coldblue.designsystem.iconpack.todo.Alarm
import com.coldblue.designsystem.iconpack.todo.Calendar
import com.coldblue.designsystem.iconpack.todo.CircleCheck
import kotlin.collections.List as ____KtList

public object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(
            Back,
            Check,
            AddSquare,
            Alarm,
            CircleCheck,
            Calendar,
            History,
            Circle,
            Home,
            Manda,
            Newzoomin,
            Newzoomout,
            Plus,
            ZoomIn,
            ZoomOut,
            Mandalart
        )
        return __AllIcons!!
    }
