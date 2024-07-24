package com.coldblue.designsystem.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack

public val IconPack.ArrowsRight: ImageVector
    get() {
        if (_arrowsRight != null) {
            return _arrowsRight!!
        }
        _arrowsRight = Builder(name = "ArrowsRight", defaultWidth = 512.0.dp, defaultHeight =
                512.0.dp, viewportWidth = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(202.1f, 450.0f)
                arcToRelative(15.0f, 15.0f, 0.0f, false, true, -10.6f, -25.61f)
                lineTo(365.79f, 250.1f)
                lineTo(191.5f, 75.81f)
                arcTo(15.0f, 15.0f, 0.0f, false, true, 212.71f, 54.6f)
                lineToRelative(184.9f, 184.9f)
                arcToRelative(15.0f, 15.0f, 0.0f, false, true, 0.0f, 21.21f)
                lineToRelative(-184.9f, 184.9f)
                arcTo(15.0f, 15.0f, 0.0f, false, true, 202.1f, 450.0f)
                close()
            }
        }
        .build()
        return _arrowsRight!!
    }

private var _arrowsRight: ImageVector? = null
