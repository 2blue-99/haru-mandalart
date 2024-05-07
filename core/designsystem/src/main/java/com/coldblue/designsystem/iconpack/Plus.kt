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

public val IconPack.Plus: ImageVector
    get() {
        if (_plus != null) {
            return _plus!!
        }
        _plus = Builder(name = "Plus", defaultWidth = 38.0.dp, defaultHeight = 39.0.dp,
            viewportWidth = 38.0f, viewportHeight = 39.0f).apply {
            path(fill = SolidColor(Color(0xFFBFC3C8)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(19.0f, 0.0f)
                lineTo(19.0f, 0.0f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 20.0f, 1.0f)
                lineTo(20.0f, 38.0f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 19.0f, 39.0f)
                lineTo(19.0f, 39.0f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 18.0f, 38.0f)
                lineTo(18.0f, 1.0f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 19.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFBFC3C8)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(0.0f, 19.0f)
                lineTo(0.0f, 19.0f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 1.0f, 18.0f)
                lineTo(37.0f, 18.0f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 38.0f, 19.0f)
                lineTo(38.0f, 19.0f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 37.0f, 20.0f)
                lineTo(1.0f, 20.0f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 0.0f, 19.0f)
                close()
            }
        }
        .build()
        return _plus!!
    }

private var _plus: ImageVector? = null
