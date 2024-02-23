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

public val IconPack.Manda: ImageVector
    get() {
        if (_manda != null) {
            return _manda!!
        }
        _manda = Builder(name = "Manda", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.9098f, 6.66f)
                lineTo(6.9998f, 3.2599f)
                verticalLineTo(3.0f)
                curveTo(6.9998f, 2.45f, 6.5498f, 2.0f, 5.9998f, 2.0f)
                curveTo(5.5698f, 2.0f, 5.2098f, 2.28f, 5.0698f, 2.66f)
                lineTo(5.0098f, 2.64f)
                verticalLineTo(2.98f)
                curveTo(5.0098f, 2.98f, 5.0098f, 2.99f, 5.0098f, 3.0f)
                verticalLineTo(21.0f)
                curveTo(5.0098f, 21.55f, 5.4598f, 22.0f, 6.0098f, 22.0f)
                curveTo(6.5598f, 22.0f, 7.0098f, 21.55f, 7.0098f, 21.0f)
                verticalLineTo(13.6899f)
                lineTo(18.0398f, 9.53f)
                curveTo(18.6398f, 9.28f, 19.0098f, 8.7f, 18.9798f, 8.06f)
                curveTo(18.9598f, 7.41f, 18.5498f, 6.86f, 17.9098f, 6.65f)
                verticalLineTo(6.66f)
                close()
                moveTo(7.0098f, 11.55f)
                verticalLineTo(5.35f)
                lineTo(16.0098f, 8.15f)
                lineTo(7.0098f, 11.54f)
                verticalLineTo(11.55f)
                close()
            }
        }
        .build()
        return _manda!!
    }

private var _manda: ImageVector? = null
