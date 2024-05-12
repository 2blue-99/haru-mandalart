package com.coldblue.designsystem.iconpack.todo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack

public val IconPack.AddSquare: ImageVector
    get() {
        if (_addSquare != null) {
            return _addSquare!!
        }
        _addSquare = Builder(name = "AddSquareLight", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.0f, 3.5f)
                horizontalLineTo(17.0f)
                curveTo(18.933f, 3.5f, 20.5f, 5.067f, 20.5f, 7.0f)
                verticalLineTo(17.0f)
                curveTo(20.5f, 18.933f, 18.933f, 20.5f, 17.0f, 20.5f)
                horizontalLineTo(7.0f)
                curveTo(5.067f, 20.5f, 3.5f, 18.933f, 3.5f, 17.0f)
                verticalLineTo(7.0f)
                curveTo(3.5f, 5.067f, 5.067f, 3.5f, 7.0f, 3.5f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.0f, 8.0f)
                lineTo(12.0f, 16.0f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF222222)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(16.0f, 12.0f)
                lineTo(8.0f, 12.0f)
            }
        }
        .build()
        return _addSquare!!
    }

private var _addSquare: ImageVector? = null
