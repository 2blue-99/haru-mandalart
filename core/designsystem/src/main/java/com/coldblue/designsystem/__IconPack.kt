package com.coldblue.designsystem

import androidx.compose.ui.graphics.vector.ImageVector
import com.coldblue.designsystem.iconpack.Check
import com.coldblue.designsystem.iconpack.History
import com.coldblue.designsystem.iconpack.Home
import com.coldblue.designsystem.iconpack.Manda
import com.coldblue.designsystem.iconpack.Newzoomin
import com.coldblue.designsystem.iconpack.Newzoomout
import com.coldblue.designsystem.iconpack.Plus
import com.coldblue.designsystem.iconpack.ZoomIn
import com.coldblue.designsystem.iconpack.ZoomOut
import kotlin.collections.List as ____KtList

public object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Check, History, Home, Manda, Newzoomin, Newzoomout, Plus, ZoomIn, ZoomOut)
    return __AllIcons!!
  }
